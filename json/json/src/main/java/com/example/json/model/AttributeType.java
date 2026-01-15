package com.example.json.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

//cuando serialices Attribute y AttributeType, ignora hibernateLazyInitializer y handler
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//marca esta clase como entidad jpa
@Entity
//tabla donde se guardan los tipos de atributo
@Table(name = "attribute_type")
public class AttributeType {

    // id autoincrement y ya, lo típico
    //identificador unico del tipo de atributo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del tipo: "STRING", "NUMERIC", "NODE", "BOOLEAN", "APPLICATIONS_ALLOWED", etc.
    //Con esto, JPA deja de decirle a la BD que ese campo sea único
    //nombre del tipo, super importante porque define como se interpreta el valor en config
    @Column(name = "type", nullable = false)
    private String type;

    // Si ese tipo se usa como lista (por ejemplo lista de números)
    //marca si el atributo es una lista, para exportarlo como array en el json
    @Column(name = "is_list", nullable = false)
    private boolean isList = false;

    // Si ese tipo es un enum (o sea, tiene valores restringidos en otra tabla)
    //indica si el tipo tiene valores cerrados (otra tabla), util para validaciones
    @Column(name = "is_enum", nullable = false)
    private boolean isEnum = false;

    // Relación con los valores del enum (si isEnum = true)
    // mappedBy = "attributeType" hace referencia al nombre del atributo en AttributeTypeValue
    //lista de valores validos para este tipo si es enum, usado para controlar que no entren cosas raras
    @OneToMany(mappedBy = "attributeType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AttributeTypeValue> values = new ArrayList<>();

    //constructor vacio para jpa
    public AttributeType() {
    }

    //constructor rapido para crear tipos con flags de lista y enum
    public AttributeType(String type, boolean isList, boolean isEnum) {
        this.type = type;
        this.isList = isList;
        this.isEnum = isEnum;
    }

    @Override
    public String toString() {
        return "AttributeType{" +
                "type='" + type + '\'' +
                ", isList=" + isList +
                ", isEnum=" + isEnum +
                ", id=" + id +
                '}';
    }

    //devuelve el id del tipo
    public Long getId() {
        return id;
    }

    //devuelve el nombre del tipo (string, numeric, node...)
    public String getType() {
        return type;
    }

    //permite cambiar el nombre del tipo, aunque normalmente no se hace porque afecta a toda la logica
    public void setType(String type) {
        this.type = type;
    }

    //indica si es lista, se usa al exportar el json
    public boolean isList() {
        return isList;
    }

    //marca el tipo como lista o no
    public void setList(boolean list) {
        isList = list;
    }

    //indica si es enum, usado para validar y consultar valores permitidos
    public boolean isEnum() {
        return isEnum;
    }

    //cambia el flag de enum
    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    //devuelve la lista de valores permitidos si es enum
    public List<AttributeTypeValue> getValues() {
        return values;
    }

    //permite setear la lista completa de valores, util en importaciones
    public void setValues(List<AttributeTypeValue> values) {
        this.values = values;
    }
}
