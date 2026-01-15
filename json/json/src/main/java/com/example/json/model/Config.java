package com.example.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

//guarda la estructura jerarquica del json: padre, hijos, tipo, valor por defecto, posicion en arrays, etc
//cada objeto es un nodo, que puede tener un padre, hijos y atributos
@Entity
//tabla de bbdd donde se guardan los nodos de la config
@Table(name = "config")
public class Config {

    //id unico del nodo en la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nodo padre en la jerarquía (null si es un nodo raíz del JSON)
    //relacion muchos a uno con su padre, para montar el arbol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Config parent;

    // Hijos de este nodo (los que tienen parent = this)
    //lista de hijos que apuntan a este nodo como parent, esto ayuda a recorrer el arbol
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Config> children = new ArrayList<>();

    // A qué atributo corresponde este nodo: BASE, application_ids, etc.
    //relaciona el nodo con la entidad attribute, que define la clave del json
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", nullable = false)
    private Attribute attribute;

    // Valor del JSON en formato texto. Luego ya lo parsearemos según el tipo.
    // Si el tipo es NODE, normalmente esto estará null porque tendrá hijos.
    //aqui se guarda el valor por defecto del nodo tal cual viene en el json
    @Column(name = "default_value")
    private String defaultValue;

    // Estos campos están en el enunciado pero no los vamos a usar en la primera versión.
    // Los dejamos por aquí por si luego creciera el ejercicio.
    //marca a que nodo de aplicacion pertenece, por si se quiere filtrar por app
    @Column(name = "application_node")
    private String applicationNode;

    //indica si la config es personalizada respecto a la base
    @Column(name = "is_custom")
    private Boolean isCustom;

    //esto es para la clase del servicio JsonExportService
    //tipo del atributo (string, number, node, etc), se usa para exportar el json correctamente
    @ManyToOne
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeType attributeType;

    //devuelve el tipo de atributo, usado en el servicio que genera el json
    public AttributeType getAttributeType() {
        return attributeType;
    }

    //setea el tipo de atributo, normalmente desde el repositorio o servicio al construir el arbol
    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    //constructor vacio requerido por jpa para instanciar la entidad
    public Config() {
    }

    //constructor rapido para crear nodos con padre, atributo y valor por defecto
    public Config(Config parent, Attribute attribute, String defaultValue) {
        this.parent = parent;
        this.attribute = attribute;
        this.defaultValue = defaultValue;
    }

    //datos útiles (id, clave del JSON, valor)
    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                //Attribute, solo el nombre
                ", attribute=" + (attribute != null ? attribute.getName() : null) +
                ", defaultValue='" + defaultValue + '\'' +
                '}';
    }


    //devuelve el id del nodo, util al trabajar con el repositorio
    public Long getId() {
        return id;
    }

    //devuelve el nodo padre para poder subir en la jerarquia
    public Config getParent() {
        return parent;
    }

    //asigna el nodo padre, IMPORTANTE para que el arbol quede bien enlazado
    public void setParent(Config parent) {
        this.parent = parent;
    }

    //devuelve la lista de hijos, se usa al recorrer o exportar el arbol
    public List<Config> getChildren() {
        return children;
    }

    //permite reemplazar la lista de hijos, por ejemplo al construir la jerarquia desde el repositorio
    public void setChildren(List<Config> children) {
        this.children = children;
    }

    //devuelve el atributo asociado, o sea la clave del json para este nodo
    public Attribute getAttribute() {
        return attribute;
    }

    //asigna el atributo, se usa cuando se crea el nodo desde el servicio o repositorio
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    //obtiene el valor por defecto almacenado como texto
    public String getDefaultValue() {
        return defaultValue;
    }

    //cambia el valor por defecto, por ejemplo al actualizar configuraciones
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    //obtiene el identificador de nodo de aplicacion si se usa filtrado por app
    public String getApplicationNode() {
        return applicationNode;
    }

    //actualiza el nodo de aplicacion asociado
    public void setApplicationNode(String applicationNode) {
        this.applicationNode = applicationNode;
    }

    //devuelve si la config es custom respecto a la base
    public Boolean getCustom() {
        return isCustom;
    }

    //marca la config como custom o no, util para logica de override en servicios
    public void setCustom(Boolean custom) {
        isCustom = custom;
    }
}

