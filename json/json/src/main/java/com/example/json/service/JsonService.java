package com.example.json.service;

import com.example.json.model.AttributeType;
import com.example.json.model.Config;
import com.example.json.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Servicio encargado de montar el json a partir de la tabla config
//Aqui va toda la chicha de recursividad
@Service
public class JsonService {

    //logger para ver que pasa al reconstruir el json
    private static final Logger log = LoggerFactory.getLogger(JsonService.class);

    //repo para acceder a los nodos de configuracion en bd
    //esto enlaza con jsonimportservice que los crea y con configservice que tambien los gestiona
    private final ConfigRepository configRepository;

    //constructor donde inyecto el repositorio para usarlo en los metodos de construccion del json
    public JsonService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    //construye un Map<String, Object> que representa
    //el JSON completo, empezando por todos los nodos raız.

    //transaccion solo de lectura, MUY IMPORTANTE para que hibernate mantenga la sesion abierta al recorrer hijos lazy
    @Transactional(readOnly = true)
    public Map<String, Object> buildJson() {
        // Sacamos todos los nodos raız (parent_id IS NULL)
        //esto es el punto de entrada del arbol, los mismos nodos que importo en jsonimportservice
        List<Config> roots = configRepository.findByParentIsNull();

        log.info("Reconstruyendo JSON desde {} nodos raíz", roots.size());

        // Usamos LinkedHashMap para mantener el orden de insercion (no obligatorio, pero mola)
        //asi el json sale en el mismo orden en que se guardaron los nodos
        Map<String, Object> jsonRoot = new LinkedHashMap<>();

        //para cada nodo raiz lo integro en el mapa, resolviendo si es objeto, lista, etc
        for (Config root : roots) {
            mergeNodeIntoMap(jsonRoot, root);
        }

        log.debug("JSON reconstruido con {} claves en la raíz", jsonRoot.size());

        //devuelvo el mapa que representa el json final
        return jsonRoot;
    }

    //este metodo mete un nodo config dentro de un mapa json, teniendo en cuenta claves repetidas y listas
    private void mergeNodeIntoMap(Map<String, Object> map, Config config) {
        //la clave es el nombre del atributo asociado al nodo (columna attribute en config)
        String key = config.getAttribute().getName();
        //el valor se calcula segun el tipo del atributo y el defaultValue
        Object value = buildValueForConfig(config);
        //Si TRACE NO está activado, el bloque ni entra, por lo que no evalúa nada
        if (log.isTraceEnabled()) {
        //Esto me llena la consola de 200 líneas por un JSON mediano
        log.trace("Mergeando nodo config id={} key='{}' valueClass={}",
                config.getId(),
                key,
                value != null ? value.getClass().getSimpleName() : "null");}

        // ¿Ya existe algo con ese nombre?
        if (map.containsKey(key)) {
            Object existing = map.get(key);
            if (existing instanceof List) {
                // ya era lista, anadimos el valor nuevo
                ((List<Object>) existing).add(value);
            } else {
                // no era lista, lo convertimos en lista con lo viejo + lo nuevo
                List<Object> list = new ArrayList<>();
                list.add(existing);
                list.add(value);
                map.put(key, list);
            }
        } else {
            // No existe la clave todavıa
            //miro si el tipo del atributo esta marcado como lista en la bd
            boolean forceList = config.getAttribute()
                    .getAttributeType()
                    .isList();

            if (forceList) {
                // Si el tipo esta marcado como lista, lo envolvemos en una lista directamente
                List<Object> list = new ArrayList<>();
                list.add(value);
                map.put(key, list);
            } else {
                //si no es lista, guardo el valor tal cual
                map.put(key, value);
            }
        }
    }

    //construye el valor java (objeto, lista o escalar) para un nodo config concreto
    private Object buildValueForConfig(Config config) {
        //cojo el tipo desde el atributo asociado, no desde config.attributeType directamente
        AttributeType type = config.getAttribute().getAttributeType();
        //valor por defecto en texto, viene de la importacion en jsonimportservice
        String raw = config.getDefaultValue();
        //nombre del tipo en mayus para comparar mas facil
        String typeName = type.getType().toUpperCase();

        // Caso NODE: objeto con hijos
        //si el tipo base es NODE, este config representa un objeto json con hijos
        if ("NODE".equals(typeName)) {
            //mapa para el objeto hijo
            Map<String, Object> obj = new LinkedHashMap<>();

            //cojo los hijos desde la relacion en la entidad config (getChildren)
            List<Config> children = config.getChildren();
            if (children != null) {
                //para cada hijo vuelvo a mezclarlo en el mapa del objeto
                for (int i = 0; i < children.size(); i++) {
                    Config child = children.get(i);
                    mergeNodeIntoMap(obj, child);
                }
            }
            //devuelvo el objeto construido
            return obj;
        }
        // Caso lista simple
        //si el tipo esta marcado como lista pero no es un nodo, trato el raw como lista separada por comas
        if (type.isList()) {
            //si no hay valor, devuelvo lista vacıa
            if (raw == null || raw.isBlank()) {
                return Collections.emptyList();
            }
            //separo por comas el texto que guardo jsonimportservice
            String[] parts = raw.split(",");
            //lista donde voy metiendo los valores convertidos
            List<Object> list = new ArrayList<>();
            for (String part : parts) {
                //parseo cada elemento segun el tipo base (numeric, boolean, string, etc)
                list.add(parseScalar(typeName, part.trim()));
            }
            return list;
        }
        // Caso valor simple
        //si no es nodo ni lista, es un escalar que parseo segun el tipo
        return parseScalar(typeName, raw);
    }
    //convierte un string a un valor escalar segun el tipo logico
    private Object parseScalar(String typeName, String raw) {
        //si no hay valor, devuelvo null
        if (raw == null) {
            return null;
        }
        //segun el tipo decido como interpretar el texto
        switch (typeName) {
            case "NUMERIC":
                try {
                    //intento parsear a long para numeros
                    return Long.parseLong(raw);
                } catch (NumberFormatException e) {
                    //si no se puede parsear, nos rendimos y lo dejamos como texto
                    //MUY IMPORTANTE: asi no revienta el json aunque el dato venga raro
                    log.warn("No se pudo parsear '{}' como NUMERIC, se deja como texto", raw);
                    return raw;
                }
            case "BOOLEAN":
                //boolean usando el parser estandar (true/false)
                return Boolean.parseBoolean(raw);
            // STRING, enums, tipos custom, etc. => texto tal cual
            //para cualquier otro tipo devuelvo el string sin tocar
            default:
                return raw;
        }
    }
}
