package com.example.json.service;

import com.example.json.exception.AttributeTypeValueNotFoundException;
import com.example.json.model.AttributeType;
import com.example.json.model.AttributeTypeValue;
import com.example.json.repository.AttributeTypeValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//logica de los valores del tipo enumeracion
//aqui manejo los valores permitidos para tipos enum y uso el repo para la bd
@Service
public class AttributeTypeValueService {

    // logger para ver qué pasa aquí
    private static final Logger log = LoggerFactory.getLogger(AttributeTypeValueService.class);

    //repo que conecta con la tabla de valores permitidos
    //esto lo usa jsonimportservice cuando tiene que registrar valores de enums
    private final AttributeTypeValueRepository attributeTypeValueRepository;

    //constructor donde inyecto el repo
    public AttributeTypeValueService(AttributeTypeValueRepository attributeTypeValueRepository) {
        this.attributeTypeValueRepository = attributeTypeValueRepository;
    }

    //devuelve todos los valores de todos los tipos, util para debug o administracion
    public List<AttributeTypeValue> findAll() {
        return attributeTypeValueRepository.findAll();
    }

    //busca un valor concreto por id, si no existe lanzo excepcion (MUY IMPORTANTE para evitar nulos)
    public AttributeTypeValue findById(Long id) {
        return attributeTypeValueRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("AttributeTypeValue no encontrado con id: {}", id);
                    return new AttributeTypeValueNotFoundException(
                            "AttributeTypeValue no encontrado con id: " + id
                    );
                });
    }

    //crea o actualiza un valor permitido para un tipo enum
    public AttributeTypeValue save(AttributeTypeValue value) {
        return attributeTypeValueRepository.save(value);
    }

    //borra un valor permitido por id
    public void delete(Long id) {
        attributeTypeValueRepository.deleteById(id);
    }

    //generico: si no existe el valor para ese tipo enum, lo crea
    //esto enlaza con AttributeTypeRepository porque el valor pertenece a un tipo concreto
    public AttributeTypeValue findOrCreate(AttributeType type, String value) {
        return attributeTypeValueRepository
                //miro si ya existe un valor con ese texto dentro del mismo tipo enum
                .findByAttributeTypeAndValue(type, value)
                //si no existe lo creo nuevo
                .orElseGet(() -> {
                    //esto si se quita salen los logs mas limpios
                    log.debug("Creando nuevo AttributeTypeValue '{}' para tipo '{}'",
                            value, type.getType());

                    //instancio nuevo valor permitido
                    AttributeTypeValue v = new AttributeTypeValue();
                    //asigno el tipo de atributo al que pertenece
                    v.setAttributeType(type);
                    //pongo el valor, plan "ACTIVE", "ALL", "NONE", etc
                    v.setValue(value);
                    //lo guardo en la bd y lo devuelvo
                    return attributeTypeValueRepository.save(v);
                });
    }
}
