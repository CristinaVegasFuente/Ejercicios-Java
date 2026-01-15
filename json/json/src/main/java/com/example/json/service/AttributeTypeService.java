package com.example.json.service;

import com.example.json.exception.AttributeTypeNotFoundException;
import com.example.json.model.AttributeType;
import com.example.json.repository.AttributeTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//logica que relaciona los tipos con los atributos
//y dice como debe comportarse un atributo segun su tipo
@Service
public class AttributeTypeService {

    //logger para ver que pasa cuando trabajamos con tipos
    private static final Logger log = LoggerFactory.getLogger(AttributeTypeService.class);

    //repo para acceder a la tabla de tipos de atributo
    //esto se usa cuando desde otros servicios necesito info del tipo (lista, enum, etc)
    private final AttributeTypeRepository attributeTypeRepository;

    //inyección por constructor
    //spring crea el servicio metiendo el repositorio que luego usare en los metodos
    public AttributeTypeService(AttributeTypeRepository attributeTypeRepository) {
        this.attributeTypeRepository = attributeTypeRepository;
    }

    //devuelve todos los tipos
    //esto es util para listar todos los tipos en un controlador o en el jsonimportservice
    public List<AttributeType> findAll() {
        return attributeTypeRepository.findAll();
    }

    //busca por id, o peta con RuntimeException si no existe
    //MUY IMPORTANTE porque asi controlo que no se trabaje con tipos inexistentes
    public AttributeType findById(Long id) {
        Optional<AttributeType> opt = attributeTypeRepository.findById(id);
        return opt.orElseThrow(() -> {
            log.warn("AttributeType no encontrado con id: {}", id);
            return new AttributeTypeNotFoundException("AttributeType no encontrado con id: " + id);
        });
    }

    //crea o actualiza
    //esto se apoya en jpa para hacer insert o update segun si tiene id
    public AttributeType save(AttributeType attributeType) {
        return attributeTypeRepository.save(attributeType);
    }

    //borra
    //aqui borro un tipo por id, por ejemplo desde un endpoint de administracion
    public void delete(Long id) {
        attributeTypeRepository.deleteById(id);
    }

    //busca un tipo por nombre y banderas (lista, enum)
    //si existe lo devuelve, si no lo crea nuevo y lo guarda
    //esto se usa desde jsonimportservice para reutilizar tipos y no duplicarlos
    public AttributeType findOrCreate(String typeName, boolean isList, boolean isEnum) {
        return attributeTypeRepository
                //busco en el repo por los tres campos clave: nombre, si es lista y si es enum
                .findByTypeAndIsListAndIsEnum(typeName, isList, isEnum)
                //si no lo encuentra, creo uno nuevo con orElseGet
                .orElseGet(() -> {
                    //instancio un tipo de atributo nuevo
                    AttributeType type = new AttributeType();
                    //le pongo el nombre del tipo, por ejemplo "STRING" o "NUMERIC"
                    type.setType(typeName);
                    //marco si este tipo representa una lista
                    type.setList(isList);
                    //marco si este tipo representa un enum
                    type.setEnum(isEnum);
                    //guardo el tipo nuevo en la bd a traves del repositorio
                    return attributeTypeRepository.save(type);
                });
    }
}
