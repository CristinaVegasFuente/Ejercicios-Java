package com.example.json.repository;

import com.example.json.model.AttributeType;
import com.example.json.model.AttributeTypeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// marco esta interfaz como repo de spring pa que se autogestione y se pueda inyectar en servicios
// Repository para los valores de los enums
public interface AttributeTypeValueRepository extends JpaRepository<AttributeTypeValue, Long> {

    // obtener todos los valores de un tipo concreto (por ejemplo todas las opciones de "APPLICATIONS_ALLOWED")
    // aqui engancho AttributeTypeValue con AttributeType, asi el servicio puede validar si un valor existe dentro del enum
    // esto se usa en el servicio para NO meter valores que no esten permitidos en la tabla de enums
    Optional<AttributeTypeValue> findByAttributeTypeAndValue(AttributeType attributeType, String value);
}

