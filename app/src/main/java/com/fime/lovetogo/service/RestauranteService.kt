package com.fime.lovetogo.service

import android.util.Log
import com.fime.lovetogo.domain.repository.RestauranteRepository
import com.fime.lovetogo.domain.structs.StructAlimento
import com.fime.lovetogo.domain.structs.StructDireccion
import com.fime.lovetogo.domain.structs.StructRestaurante
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RestauranteService {

    var repository: RestauranteRepository = RestauranteRepository()

    var alimentosRestaurante: ArrayList<StructAlimento> = ArrayList()

    constructor()

    fun registrar(restaurante: StructRestaurante, user: FirebaseUser) {
        restaurante.id = user.uid
        repository.save(restaurante)
    }

    fun registrarDireccion(direccion: StructDireccion, idRestaurante: String) {
        repository.saveDireccion(direccion, idRestaurante)
    }

    fun registrarPlatillo(platillo: StructAlimento, idRestaurante: String) {
        repository.savePlatillo(platillo, idRestaurante)
    }

    fun consultaPlatillosPorRestaurante(idRestaurante: String) : ArrayList<StructAlimento> {
        val database = repository.getPlatillosByRestaurante(idRestaurante)
        val alimentos: ArrayList<StructAlimento> = ArrayList()
        database.get().addOnSuccessListener {
            for (alimentoSnapshot in it.children) {
                val alimento = StructAlimento()
                alimento.id = alimentoSnapshot.child("id").getValue(String::class.java)!!
                alimento.nombre = alimentoSnapshot.child("nombre").getValue(String::class.java)!!
                alimento.caducidad = alimentoSnapshot.child("caducidad").getValue(String::class.java)!!
                alimento.cantidad = alimentoSnapshot.child("cantidad").getValue(Int::class.java)!!
                alimentos.add(alimento)
            }
            alimentosRestaurante = alimentos

        }.addOnFailureListener{
            Log.e("Consulta Menu", "Error al consultar los datos", it)
        }
        return alimentos
    }

    companion object {
        private const val TAG = "RestauranteService"
    }
}