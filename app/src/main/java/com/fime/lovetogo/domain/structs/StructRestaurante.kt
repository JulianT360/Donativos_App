package com.fime.lovetogo.domain.structs

import java.io.Serializable

class StructRestaurante : Serializable {
    var id: String = ""
    var nombre: String = ""
    var email: String = ""
    var telefono: String = ""
    var password: String = ""
    var passwordConfirm: String = ""
    var direccion: StructDireccion?=null
    var platillos: List<StructAlimento>?=null

    constructor()

    constructor(nombre: String, email: String, telefono: String, password: String,
                passwordConfirm: String) {
        this.nombre = nombre
        this.email = email
        this.telefono = telefono
        this.password = password
        this.passwordConfirm = passwordConfirm
    }

    constructor(nombre: String, email: String, telefono: String, password: String,
                passwordConfirm: String, direccion: StructDireccion) {
        this.nombre = nombre
        this.email = email
        this.telefono = telefono
        this.password = password
        this.passwordConfirm = passwordConfirm
        this.direccion = direccion
    }
}