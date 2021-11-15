package com.fime.lovetogo.domain.structs

import java.io.Serializable

class StructAlimento : Serializable {
    var id: String = ""
    var nombre: String = ""
    var cantidad: Int?=0
    var caducidad: String = ""

    constructor()

    constructor(nombre: String, cantidad: Int, caducidad: String) {
        this.nombre = nombre
        this.cantidad = cantidad
        this.caducidad = caducidad
    }
}