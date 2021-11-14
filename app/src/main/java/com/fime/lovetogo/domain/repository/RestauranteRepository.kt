package com.fime.lovetogo.domain.repository

import com.fime.lovetogo.domain.structs.StructAlimento
import com.fime.lovetogo.domain.structs.StructDireccion
import com.fime.lovetogo.domain.structs.StructRestaurante
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RestauranteRepository {

    var database: FirebaseDatabase = Firebase.database

    constructor()

    fun save(restaurante: StructRestaurante) {
        val myRef = database.getReference("restaurante/${restaurante.id}")
        myRef.setValue(restaurante)
    }

    fun saveDireccion(direccion: StructDireccion, idRestaurante: String) {
        val referenciaDireccion = database.getReference("restaurante/${idRestaurante}/direccion")
        referenciaDireccion.setValue(direccion)
    }

    fun savePlatillo(platillo: StructAlimento, idRestaurante: String) {
        val refAlimento = database.getReference("restaurante/${idRestaurante}/menu")
        val platilloID = refAlimento.push().key
        platillo.id = platilloID!!
        refAlimento.child(platilloID).setValue(platillo)
    }

    fun getPlatillosByRestaurante(idRestaurante: String): DatabaseReference {
        return database.getReference("restaurante/${idRestaurante}/menu")
    }
}