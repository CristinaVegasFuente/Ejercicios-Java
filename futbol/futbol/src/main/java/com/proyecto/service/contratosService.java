package com.proyecto.service;

import com.proyecto.entity.Contratos;
import com.proyecto.repository.contratosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class contratosService {

    private final contratosRepository contratosRepository;

    @Autowired
    public contratosService(contratosRepository contratosRepository) {
        this.contratosRepository = contratosRepository;
    }

    // Leer todos
    public List<Contratos> getContratos() {
        return contratosRepository.findAll();
    }

    //Lee por ID
    public Optional<Contratos> getContratoById(int id) {
        return contratosRepository.findById(id);
    }

    //Crea/Guarda
    public Contratos saveContrato(Contratos contrato) {
        return contratosRepository.save(contrato);
    }

    //Elimina
    public void deleteContrato(int id) {
        contratosRepository.deleteById(id);
    }

    // Verificar existencia
    public boolean existsById(int id) {
        return contratosRepository.existsById(id);
    }
}
