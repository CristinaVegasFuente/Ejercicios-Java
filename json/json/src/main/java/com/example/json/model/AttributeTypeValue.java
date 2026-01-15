package com.example.json.model;

import jakarta.persistence.*;

//esta clase es para los valores que son permitidos para los tipos enumerados
//en el ejemplo la lista son numeros 1,2,3,4, cada fila de la tabla represeta un valor posible dentro del tipo enumerado
//guarda valores posibles para tipos de tipo enum (por ejemplo lista de valores permitidos)
@Entity
//tabla donde se guardan los valores del enum
@Table(name = "attribute_type_value")
public class AttributeTypeValue {

    //id unico del valor permitido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Valor permitido, por ejemplo "ALL", "NONE", "PENDING", etc.
    //aqui se guarda el texto exacto del valor permitido dentro del enum
    @Column(name = "value", nullable = false)
    private String value;

    // A qué tipo de atributo pertenece este valor (el enum al que pertenece)
    //relacion con attributeType para saber a que enum pertenece este valor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeType attributeType;

    //constructor vacio para jpa
    public AttributeTypeValue() {
    }

    //constructor para crear valores con su tipo asociado
    public AttributeTypeValue(String value, AttributeType attributeType) {
        this.value = value;
        this.attributeType = attributeType;
    }

    @Override
    public String toString() {
        return "AttributeTypeValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    //devuelve el id del valor permitido
    public Long getId() {
        return id;
    }

    //devuelve el texto del valor permitido
    public String getValue() {
        return value;
    }

    //permite cambiar el valor permitido, aunque no es habitual
    public void setValue(String value) {
        this.value = value;
    }

    //devuelve el tipo de atributo (el enum al que pertenece)
    public AttributeType getAttributeType() {
        return attributeType;
    }

    //asigna el tipo de atributo
    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }
}
