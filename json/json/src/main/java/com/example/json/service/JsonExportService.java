package com.example.json.service;

import com.example.json.model.AttributeType;
import com.example.json.model.Config;
import com.example.json.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

//servicio que se encarga de reconstruir el json a partir de los nodos config
@Service
public class JsonExportService {

    //logger para ver qué pasa al exportar el json
    private static final Logger log = LoggerFactory.getLogger(JsonExportService.class);

    //repo de config para ir leyendo los nodos del arbol de configuracion
    //esto enlaza con configservice, pero aqui accedo directo al repo
    private final ConfigRepository configRepository;

    //constructor donde inyecto el repositorio de config
    public JsonExportService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    //reconstruye
    //este metodo monta el json entero como un mapa clave/valor KEY:VALUE
    public Map<String, Object> exportJson() {
        //uso linkedhashmap para ordenar los nodos
        Map<String, Object> result = new LinkedHashMap<>();

        //Busca los nodos raíz
        //aqui obtengo todos los nodos que no tienen padre, el inicio del arbol
        List<Config> roots = configRepository.findByParentIsNull();

        log.info("Exportando JSON desde {} nodos raíz", roots.size());

        //recorro cada nodo raiz para meter su valor y meterlo en el mapa
        for (int i = 0; i < roots.size(); i++) {
            Config root = roots.get(i);
            //nombre del atributo, sera la clave principal del json
            String name = root.getAttribute().getName();
            //buildnode crea el valor correspondiente segun el tipo (nodo, lista, primitivo)
            Object value = buildNode(root);
            //meto la pareja nombre/valor en el mapa final
            result.put(name, value);
        }

        log.debug("JSON exportado con {} claves en la raíz", result.size());

        //devuelvo el json reconstruido como mapa
        return result;
    }

    //arma el json desde un nodo
    //este metodo es recursivo si el nodo tiene hijos baja por el arbol
    private Object buildNode(Config config) {

        //tipo del atributo se usa para decidir como interpretar el valor
        AttributeType type = config.getAttributeType();
        //valor por defecto en forma de string lo convertire segun el tipo
        String raw = config.getDefaultValue();

        //si es un nodo objeto
        //cuando el tipo es NODE, significa que tiene hijos y se convierte en objeto json
        if (type.getType().equals("NODE")) {
            //mapa para representar el objeto hijo (clave = nombre atributo, valor = valor)
            Map<String, Object> obj = new LinkedHashMap<>();

            //saco los hijos del nodo actual usando el repo de config
            List<Config> children = configRepository.findByParent(config);
            //para cada hijo, construyo su valor recursivamente
            for (int i = 0; i < children.size(); i++) {
                Config child = children.get(i);
                //nombre del atributo hijo
                String childName = child.getAttribute().getName();
                //valor del hijo
                Object childValue = buildNode(child);
                //lo meto en el objeto
                obj.put(childName, childValue);
            }
            //devuelvo el objeto completo
            return obj;
        }

        //si es una lista
        //convierto el string en una lista de elementos
        if (type.isList()) {
            //si no hay valor devuelvo lista vacia y a correr
            if (raw == null || raw.isBlank()) return Collections.emptyList();
            //separo por comas los valores de la lista
            String[] parts = raw.split(",");
            //lista donde meto los elementos ya convertidos
            List<Object> list = new ArrayList<>();

            //para cada trozo, lo normalizo y lo convierto segun el tipo base
            for (int i = 0; i < parts.length; i++) {
                String p = parts[i];
                list.add(convertPrimitive(p.trim(), type));
            }
            //devuelvo la lista final
            return list;
        }

        //si es primitivo
        //lo trato como valor simple (numero, boolean o string)
        return convertPrimitive(raw, type);
    }

    //Convierte STRING a boolean/number/string según el tipo
    //aqui hago la conversion de la cadena al tipo java correspondiente
    private Object convertPrimitive(String raw, AttributeType type) {
        //si no hay valor devuelvo null
        if (raw == null) return null;

        //segun el tipo logico defino como se ve el string
        switch (type.getType()) {
            case "NUMERIC":
                //primero intento parsear a entero
                try {
                    return Integer.valueOf(raw);
                } catch (NumberFormatException e) {
                    //si peta, pruebo con double por si tiene decimales
                    try {
                        return Double.valueOf(raw);
                    } catch (NumberFormatException e2) {
                        //si también peta, lo dejo como texto y lo aviso en logs
                        log.warn("No se pudo parsear '{}' como NUMERIC, se deja como String", raw);
                        return raw;
                    }
                }
            case "BOOLEAN":
                //para boolean uso el parser de java (true/false)
                return Boolean.valueOf(raw);
            default:
                //para string o enum devuelvo tal cual la cadena
                return raw;
        }
    }
}
