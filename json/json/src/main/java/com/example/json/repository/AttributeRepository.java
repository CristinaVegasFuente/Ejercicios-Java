package com.example.json.repository;

import com.example.json.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// uso esto para marcar la interfaz como repo de spring, asi spring la detecta y la inyecta donde toca
// Repository para los nombres de los atributos
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    // Buscar un atributo por su nombre "BASE", "application_ids", etc.
    // aqui enlazo directamente con la entidad Attribute, asi el servicio puede pedir el atributo por nombre sin liarse
    Optional<Attribute> findByName(String name);
}
