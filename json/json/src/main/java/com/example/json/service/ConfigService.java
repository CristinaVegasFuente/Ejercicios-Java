package com.example.json.service;

import com.example.json.exception.ConfigNotFoundException;
import com.example.json.model.Config;
import com.example.json.repository.ConfigRepository;
//la anotacion correcta de spring es esta y no la de jakarta ya que no soporta readOnly=true
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

//es la configuracion de los nodos, con su valor, tipo y posicion en el arbol
//aqui gestiono los nodos que reconstruyen el json final, usando el repo
@Service
public class ConfigService {

    //repo para acceder a la tabla de configuraciones (los nodos del arbol)
    //esto se usa cuando jsonimportservice o algun controlador necesita leer o montar el arbol
    private final ConfigRepository configRepository;

    //constructor donde inyecto el repo
    public ConfigService(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    //devuelve todos los nodos de configuracion
    //sirve para debug o endpoints de administracion
    public List<Config> findAll() {
        return configRepository.findAll();
    }

    //busca un nodo concreto por id
    //MUY IMPORTANTE: si no existe lanzo excepcion para no trabajar con configuraciones rotas
    public Config findById(Long id) {
        return configRepository.findById(id)
                .orElseThrow(() ->
                        new ConfigNotFoundException("Config no encontrado con id: " + id)
                );
    }
    //@Transactional(readOnly = true)
    //Eso hace que, mientras Jackson está accediendo a las relaciones LAZY (getChildren(), getAttribute(), etc.),
    //la sesión de Hibernate siga abierta y no haya problema
    @Transactional(readOnly = true)
    //saco todos los nodos raiz, los que no tienen padre
    //esto es clave porque es desde donde arranca el arbol de configuracion
    public List<Config> findRootNodes() {
        return configRepository.findByParentIsNull();
    }

    //guardo o actualizo un nodo, jpa se encarga de insert/update
    public Config save(Config config) {
        return configRepository.save(config);
    }

    //borro un nodo por id, esto cascara si tiene hijos dependiendo del mapeo, asi que OJO
    public void delete(Long id) {
        configRepository.deleteById(id);
    }
}
