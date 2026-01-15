package com.example.json.repository;

import com.example.json.model.AttributeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// esto marca la interfaz como repo spring, asi puedo inyectarla facil en los servicios y controladores
// Repository para trabajar con los tipos de atributo
public interface AttributeTypeRepository extends JpaRepository<AttributeType, Long> {

    //por si quiero buscar un tipo por su nombre, plan "STRING", "NUMERIC", etc
    // aqui filtro tambien por isList e isEnum porque en la entidad esos flags cambian como se usa el tipo
    // SUPER IMPORTANTE: esto lo usa el servicio para distinguir tipos simples, listas o enums dentro del modelo
    Optional<AttributeType> findByTypeAndIsListAndIsEnum(String type,
                                                         boolean isList,
                                                         boolean isEnum);
}
