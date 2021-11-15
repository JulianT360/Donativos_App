package com.fime.lovetogo.fragments.app.restaurant

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fime.lovetogo.R
import com.fime.lovetogo.adaptadores.AlimentoAdapter
import com.fime.lovetogo.domain.repository.RestauranteRepository
import com.fime.lovetogo.domain.structs.StructAlimento
import com.fime.lovetogo.service.RestauranteService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class ListaAlimentosFragment : Fragment {

    private lateinit var auth: FirebaseAuth

    var recyclerView: RecyclerView?=null
    var restauranteRepository: RestauranteRepository?=null
    var onClickListenerExterno: View.OnClickListener?=null

    constructor(listExt: View.OnClickListener) {
        onClickListenerExterno=listExt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        restauranteRepository = RestauranteRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_alimentos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.currentUser!!
        this.consultarPlatillosPorRestauranteListener(user.uid, view)
    }

    private fun consultarPlatillosPorRestauranteListener(idRestaurante: String, view:View) {
        val database = restauranteRepository!!.getPlatillosByRestaurante(idRestaurante)

        val alimentosListener = object: ValueEventListener {
            override fun onDataChange(alimentosSnapshot: DataSnapshot) {
                val alimentos:ArrayList<StructAlimento> = ArrayList()
                for (alimentoSnapshot in alimentosSnapshot.children) {

                    val alimento = StructAlimento()
                    alimento.id = alimentoSnapshot.child("id").getValue(String::class.java)!!
                    alimento.nombre = alimentoSnapshot.child("nombre").getValue(String::class.java)!!
                    alimento.caducidad = alimentoSnapshot.child("caducidad").getValue(String::class.java)!!
                    alimento.cantidad = alimentoSnapshot.child("cantidad").getValue(Int::class.java)!!
                    alimentos.add(alimento)
                }

                val adapter = AlimentoAdapter()
                adapter.alimentos = alimentos
                recyclerView = view.findViewById(R.id.lista_alimentos)
                recyclerView!!.layoutManager = LinearLayoutManager(context)
                recyclerView!!.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("Listar Alimentos", "loadPost:onCancelled", error.toException())
            }
        }
        database.addValueEventListener(alimentosListener)
    }
}