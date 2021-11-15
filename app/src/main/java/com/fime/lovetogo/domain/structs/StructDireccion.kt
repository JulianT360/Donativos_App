package com.fime.lovetogo.domain.structs

import java.io.Serializable

class StructDireccion : Serializable {
    var calle: String = ""
    var numeroExterior: String = ""
    var numeroInterior: String = ""
    var colonia: String = ""
    var codigoPostal: String = ""
    var municipio: String = ""
    var estado: String = ""

    constructor(calle: String, numeroExterior: String, numeroInterior: String, colonia: String,
                codigoPostal: String, municipio: String, estado: String) {
        this.calle = calle
        this.numeroExterior = numeroExterior
        this.numeroInterior = numeroInterior
        this.colonia = colonia
        this.codigoPostal = codigoPostal
        this.municipio = municipio
        this.estado = estado
    }
}