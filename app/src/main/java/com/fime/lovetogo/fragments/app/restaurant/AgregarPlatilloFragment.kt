package com.fime.lovetogo.fragments.app.restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.fime.lovetogo.R

class AgregarPlatilloFragment : Fragment {

    var etNombre: EditText?=null
    var etCaducidad: EditText?=null
    var etCantidad: EditText?=null

    var btnAgregar: Button?=null

    var onClickListenerExterno: View.OnClickListener?=null

    constructor(listExt: View.OnClickListener) {
        onClickListenerExterno=listExt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_platillo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNombre = view.findViewById(R.id.etNombreAlimento)
        etCaducidad = view.findViewById(R.id.etCaducidadAlimento)
        etCantidad = view.findViewById(R.id.etCantidadAlimento)

        btnAgregar = view.findViewById(R.id.btnAgregarAlimento)

        btnAgregar!!.setOnClickListener(onClickListenerExterno)
    }
}