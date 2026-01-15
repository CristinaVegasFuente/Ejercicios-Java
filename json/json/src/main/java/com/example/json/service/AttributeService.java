package com.example.json.service;

import com.example.json.exception.AttributeNotFoundException;
import com.example.json.model.Attribute;
import com.example.json.model.AttributeType;
import com.example.json.repository.AttributeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//logica de los atributos, aqui tiro del repositorio para manejar la bd
@Service
public class AttributeService {

    //logger para ver que pasa cuando trabajamos con atributos
    private static final Logger log = LoggerFactory.getLogger(AttributeService.class);

    //instancio el repo que conecta con la tabla de atributos
    private final AttributeRepository attributeRepository;

    //constructor donde inyecto el repo para usarlo en todos los metodos
    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    //saco todos los atributos de la bd, esto lo usa el controlador para listar
    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    //busco un atributo por id, MUY IMPORTANTE porque si no existe lanzo excepcion
    public Attribute findById(Long id) {
        return attributeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Atributo no encontrado con id: {}", id);
                    return new AttributeNotFoundException("Atributo no encontrado con id: " + id);
                });
    }

    //guardo o actualizo un atributo usando el repo
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    //elimino un atributo por id, esto va directo al repo
    public void delete(Long id) {
        attributeRepository.deleteById(id);
    }

    //busco atributo por nombre, esto es util para jsonimportservice
    public Optional<Attribute> findByName(String name) {
        return attributeRepository.findByName(name);
    }

    //Busca un Attribute por nombre
    // Si existe, lo devuelve.
    //Si no existe, lo crea con el tipo que le pasamos.
    //Esto lo usará JsonImportService para no estar duplicando atributos.

    //metodo clave para no duplicar atributos al importar json
    public Attribute findOrCreate(String name, AttributeType type) {
        //miro si ya existe un atributo con ese nombre
        Optional<Attribute> existing = attributeRepository.findByName(name);

        if (existing.isPresent()) {
            //si existe lo devuelvo tal cual
            //esto enlaza con el repo porque hace una consulta por nombre
            return existing.get();
        }

        //si no existe creo uno nuevo
        Attribute attr = new Attribute();
        attr.setName(name); //le pongo el nombre
        attr.setAttributeType(type); //y su tipo, que viene de attributetyperepository

        //lo guardo y lo devuelvo
        return attributeRepository.save(attr);
    }

}
