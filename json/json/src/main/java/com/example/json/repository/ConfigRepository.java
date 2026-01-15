package com.example.json.repository;

import com.example.json.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// marco la interfaz como repo de spring, asi spring la detecta y me la inyecta sin pelearme con nada
// Repository para los nodos de configuración (los que reconstruyen el json)
public interface ConfigRepository extends JpaRepository<Config, Long> {

    // Para sacar todos los nodos raiz (los que no tienen padre)
    // esto lo uso cuando quiero reconstruir el arbol desde arriba del todo
    List<Config> findByParentIsNull();

    // Para sacar los nodos hijos de un nodo concreto
    // aqui enlazo directamente padre → hijos, super util para navegar la jerarquia y montar el JSON completo
    List<Config> findByParent(Config parent);
}
