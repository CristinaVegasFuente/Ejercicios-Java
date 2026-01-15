package com.example.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//esta entidad representa el atributo
//guarda los nombres de los campos del json sin repetirlos
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//marca esta clase como entidad jpa
@Entity
//tabla donde se guardan los atributos del json
@Table(name = "attribute")
public class Attribute {

    //id unico del atributo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del atributo en el JSON, por ejemplo "BASE", "application_ids", etc.
    // No queremos duplicados, así que lo marcamos como unique.
    //nombre exacto del campo del json, importantisimo porque evita duplicar claves
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Tipo de dato de este atributo (STRING, NUMERIC, NODE, etc.)
    //relaciona este atributo con su tipo, igual que en la entidad config
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeType attributeType;

    // Todos los nodos de configuración que usan este atributo
    //lista de configs que usan este atributo como clave, util para recorrer el arbol inversamente
    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Config> configs = new ArrayList<>();

    //constructor vacio requerido por jpa
    public Attribute() {
    }

    //constructor rapido para crear atributos con nombre y tipo
    public Attribute(String name, AttributeType attributeType) {
        this.name = name;
        this.attributeType = attributeType;
    }

    //Sirve para imprimir la entidad por consola, logs, debugging
    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //devuelve el id del atributo
    public Long getId() {
        return id;
    }

    //devuelve el nombre que se usa como clave en el json
    public String getName() {
        return name;
    }

    //permite cambiar el nombre del atributo, aunque normalmente no se hace porque es unico
    public void setName(String name) {
        this.name = name;
    }

    //devuelve el tipo de dato del atributo
    public AttributeType getAttributeType() {
        return attributeType;
    }

    //asigna un tipo al atributo, importante para validar y exportar el json
    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    //devuelve todas las configs que usan este atributo
    public List<Config> getConfigs() {
        return configs;
    }

    //permite reemplazar la lista, por ejemplo al cargar la relacion desde el servicio
    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}


