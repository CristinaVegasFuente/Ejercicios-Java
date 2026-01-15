package com.example.json.service;

import com.example.json.exception.InvalidJsonException;
import com.example.json.model.Attribute;
import com.example.json.model.AttributeType;
import com.example.json.model.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//servicio que se encarga de LEER un json y guardarlo en las tablas (atributos, tipos, config, etc)
//lee el json y lo mete ordenadito en sus tablas
@Service
public class JsonImportService {

    //es un logger para soltar mensajitos por la consola por si falla algo
    private static final Logger log = LoggerFactory.getLogger(JsonImportService.class);

    //servicio para manejar los tipos de atributo (STRING, NUMERIC, NODE, etc)
    //esto enlaza con el repo de attributetypepara crear/buscar tipos
    private final AttributeTypeService attributeTypeService;
    //servicio de atributos, para no duplicar atributos y ligarlos a tipos
    private final AttributeService attributeService;
    //servicio de config, aqui guardo los nodos del arbol que luego usa jsonexportservice
    private final ConfigService configService;
    //servicio para los valores permitidos de enums
    private final AttributeTypeValueService attributeTypeValueService;

    //inyecta los servicios, le esta diciendo que quiere usar esos 4 servicios y fuera
    public JsonImportService(AttributeTypeService attributeTypeService,
                             AttributeService attributeService,
                             ConfigService configService,
                             AttributeTypeValueService attributeTypeValueService) {
        this.attributeTypeService = attributeTypeService;
        this.attributeService = attributeService;
        this.configService = configService;
        this.attributeTypeValueService = attributeTypeValueService;
    }
    //metodo principal, arrancador, es el punto de entrada
    @Transactional
    public void importJson(Map<String, Object> rootJson) {

        //si viene null o vacío
        if (rootJson == null || rootJson.isEmpty()) {
            //lanza un error
            throw new InvalidJsonException("El JSON de configuración está vacío o es nulo");
        }

        log.info("Empieza importación JSON con {} claves raíz", rootJson.size());

        try {
            //recorro las claves del json raiz y las proceso una a una
            for (Iterator<Map.Entry<String, Object>> iterator = rootJson.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Object> entry = iterator.next();
                //como son nodos raiz EL PARENT ES NULL
                log.debug("Procesando clave raíz '{}'", entry.getKey());
                processEntry(null, entry.getKey(), entry.getValue());
            }
            log.info("Importacion json ok");

        } catch (InvalidJsonException e) {
            //yo he pasado un JSON mal formado es culpa del cliente
            log.warn("Error de formato json: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            //Exception fallo inesperado pues culpa del backend
            log.error("Error inesperado durante la importación del json", e);
            throw new InvalidJsonException("El json de configuración no tiene el formato esperado");
        }
    }
    //metodo generico que decide si el valor es nodo, lista o primitivo
    private void processEntry(Config parentConfig, String name, Object value) {
        //si viene null no hago nada
        if (value == null) {
            log.warn("Valor null encontrado en '{}', se ignora", name);
            return;
        }

        //si es un objeto json, lo trato como nodo
        if (value instanceof Map) {
            processNode(parentConfig, name, (Map<String, Object>) value);
            //si es una lista, lo mando al procesado de listas
        } else if (value instanceof List) {
            processList(parentConfig, name, (List<?>) value);
            //si no, es un valor primitivo (string, number, boolean)
        } else {
            processPrimitive(parentConfig, name, value);
        }
    }
    //NODOS objetos
    //aqui proceso un objeto json y lo convierto en un nodo con hijos
    private void processNode(Config parent, String name, Map<String, Object> obj) {

        log.debug("NODE '{}' con {} hijos", name, obj.size());

        //busco/creo el tipo NODE, que representa un objeto con hijos
        AttributeType nodeType = attributeTypeService.findOrCreate("NODE", false, false);
        //busco/creo el atributo con ese nombre y tipo NODE
        Attribute attribute = attributeService.findOrCreate(name, nodeType);

        //creo el nodo de configuracion para este objeto
        Config configNode = new Config();
        //le asigno el padre en el arbol si es raiz va a ser null
        configNode.setParent(parent);
        //le pongo el atributo que representa el nombre del nodo
        configNode.setAttribute(attribute);
        //le asigno el tipo NODE
        configNode.setAttributeType(nodeType);
        //no tiene valor por defecto, es un contenedor
        configNode.setDefaultValue(null);
        //estos campos se quedan en null de momento
        configNode.setApplicationNode(null);
        configNode.setCustom(null);

        //guardo el nodo en la bd usando configservice (que a su vez usa configrepository)
        configNode = configService.save(configNode);

        //recorro los hijos del objeto, aqui es donde se genera el arbol completo
        for (Iterator<Map.Entry<String, Object>> iterator = obj.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Object> entry = iterator.next();
            processEntry(configNode, entry.getKey(), entry.getValue());
        }
    }
    //PRIMITIVOS
    //aqui proceso valores simples (ni listas y ni objetos)
    private void processPrimitive(Config parent, String name, Object value) {

        //convierto el valor a string para guardarlo en config.defaultValue
        String raw = String.valueOf(value);

        //ya existe el atributo????????????
        //miro si ya tengo un atributo con ese nombre, para reutilizar tipo y evitar duplicados
        Optional<Attribute> optAttr = attributeService.findByName(name);

        if (optAttr.isPresent()) {
            //si existe lo reutilizo
            Attribute attr = optAttr.get();
            AttributeType type = attr.getAttributeType();

            //Si el tipo de ese atributo es ENUM va para attribute_type_value
            //MUY IMPORTANTE!!! aqui voy registrando los valores permitidos de ese enum
            if (type.isEnum()) {
                attributeTypeValueService.findOrCreate(type, raw);
            }

            //creo el nodo config asociado a este valor
            Config config = new Config();
            config.setParent(parent);
            config.setAttribute(attr);
            config.setAttributeType(type);
            config.setDefaultValue(raw);
            config.setApplicationNode(null);
            config.setCustom(null);
            //lo guardo con configservice para que jsonexportservice lo pueda leer luego
            configService.save(config);
            return;
        }

        //No existe el atributo todavía pues se pone tipo simple
        AttributeType type;

        //si el valor es boolean, el tipo es BOOLEAN
        if (value instanceof Boolean) {
            type = attributeTypeService.findOrCreate("BOOLEAN", false, false);
            //si el valor es numero, el tipo es NUMERIC
        } else if (value instanceof Number) {
            type = attributeTypeService.findOrCreate("NUMERIC", false, false);
            //si no, lo tratamos como STRING
        } else {
            type = attributeTypeService.findOrCreate("STRING", false, false);
        }

        //creo o reutilizo el atributo con este nombre y tipo
        Attribute attribute = attributeService.findOrCreate(name, type);

        //creo el nodo config para guardar el valor
        Config config = new Config();
        config.setParent(parent);
        config.setAttribute(attribute);
        config.setAttributeType(type);
        config.setDefaultValue(raw);
        config.setApplicationNode(null);
        config.setCustom(null);

        //guardo la configuracion en la bd
        configService.save(config);
    }
    //LISTAS
    //aqui proceso listas, que pueden ser de objetos o de valores primitivos
    private void processList(Config parent, String name, List<?> list) {
        //si la lista esta vacia no devuelve nada
        if (list.isEmpty()) return;

        //miro el primer elemento para saber de que tipo es la lista
        Object first = list.get(0);

        if (first instanceof Map) {
            //en la lista de objetos
            //aqui veo que cada elemento es un nodo objeto con el mismo nombre
            for (int i = 0; i < list.size(); i++) {
                Object element = list.get(i);
                if (element instanceof Map) {
                    //cada elemento se procesa como nodo hijo
                    processNode(parent, name, (Map<String, Object>) element);
                }
            }
        } else {
            //si no son mapas, son valores primitivos
            processPrimitiveList(parent, name, list);
        }
    }

    //procesa listas de valores primitivos (strings, numeros, booleanos)
    private void processPrimitiveList(Config parent, String name, List<?> list) {
        //si esta vacia lo mismo no devuelve nada
        if (list.isEmpty()) return;

        //texto de todos los valores
        //convierto todos los elementos a string
        List<String> values = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            String s = String.valueOf(o);
            values.add(s);
        }
        //los uno por comas para guardarlos en defaultValue
        String defaultValue = String.join(",", values);

        //ya existe atributo?
        //miro si ya tengo un atributo con ese nombre
        Optional<Attribute> optAttr = attributeService.findByName(name);

        if (optAttr.isPresent()) {
            //si existe lo reutilizo
            Attribute attr = optAttr.get();
            AttributeType type = attr.getAttributeType();

            //si este tipo es ENUM+LIST metemos todos los valores en attribute_type_value
            //asi voy registrando todas las opciones posibles del enum lista
            if (type.isEnum() && type.isList()) {
                for (int i = 0; i < values.size(); i++) {
                    String v = values.get(i);
                    attributeTypeValueService.findOrCreate(type, v);
                }
            }

            //creo el nodo config para esta lista ya existente
            Config config = new Config();
            config.setParent(parent);
            config.setAttribute(attr);
            config.setAttributeType(type);
            config.setDefaultValue(defaultValue);
            config.setApplicationNode(null);
            config.setCustom(null);
            configService.save(config);
            return;
        }

        //si no existe se crea un tipo de lista simple
        //deduzco el tipo base de la lista mirando el primer elemento
        Object first = list.get(0);
        String typeName;
        if (first instanceof Boolean) typeName = "BOOLEAN";
        else if (first instanceof Number) typeName = "NUMERIC";
        else typeName = "STRING";

        //creo un tipo que sea lista de ese tipo base
        AttributeType listType = attributeTypeService.findOrCreate(typeName, true, false);
        //creo el atributo que representa esta lista
        Attribute attr = attributeService.findOrCreate(name, listType);

        //creo el nodo config para la lista nueva
        Config config = new Config();
        config.setParent(parent);
        config.setAttribute(attr);
        config.setAttributeType(listType);
        config.setDefaultValue(defaultValue);
        config.setApplicationNode(null);
        config.setCustom(null);

        //guardo la configuracion de la lista
        configService.save(config);
    }
}
