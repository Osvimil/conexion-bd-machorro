package com.appspot.conexionendpoints.backend;

import com.googlecode.objectify.Key;

import com.googlecode.objectify.annotation.Entity;

import com.googlecode.objectify.annotation.Id;

import com.googlecode.objectify.annotation.Index;

/** Define el contenido del almacenamiento en la base de datos. */

@Entity

@SuppressWarnings("unused")

public class Conocido {

    /**

     * Indica la llave primaria. Cuando es de tipo Long, y vale null al agregar,

     * se genera el valor automáticamente.

     */

    @Id

    Long id;

    /**

     * La anotación Index define un índice de búsqueda en la base de datos. Es

     * requerida para los campos que se usan en las consultas.

     */

    @Index

    String nombre;

    // Los nombres de los campos que se convierten a JSON no llevan acentos.

    String telefono;

    public Conocido() {}
    public Conocido(String id, String nombre, String telefono) {

        this.id = id == null ? null : Key.valueOf(id).getId();

        this.nombre = nombre;

        this.telefono = telefono;

    }

    public String getId() {

        return id == null ? null : Key.create(Conocido.class, id).toWebSafeString();

    }

    public void setId(String id) {

        this.id = id == null ? null : Key.valueOf(id).getId();

    }

    public String getNombre() {

        return nombre;

    }

    public void setNombre(String nombre) {

        this.nombre = nombre;

    }

    public String getTelefono() {

        return telefono;

    }

    public void setTelefono(String telefono) {

        this.telefono = telefono;

    }

}