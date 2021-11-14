package com.fime.lovetogo.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.fime.lovetogo.R

class RegistroContactoFragment : Fragment {

    var etCalle: EditText?=null
    var etNumeroInterior: EditText?=null
    var etNumeroExterior: EditText?=null
    var etColonia: EditText?=null
    var etMunicipio: EditText?=null
    var etEstado: EditText?=null
    var codigoPostal: EditText?=null
    var btnRegistroDatosContacto: Button?=null

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
        return inflater.inflate(R.layout.fragment_registro_contacto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etCalle = view.findViewById(R.id.etCalle)
        etNumeroExterior = view.findViewById(R.id.etNumeroExterior)
        etNumeroInterior = view.findViewById(R.id.etNumeroInterior)
        codigoPostal = view.findViewById(R.id.etCodigoPostal)
        etColonia = view.findViewById(R.id.etColonia)
        etMunicipio = view.findViewById(R.id.etMunicipio)
        etEstado = view.findViewById(R.id.etEstado)
        btnRegistroDatosContacto = view.findViewById(R.id.btnRegistroDatosContacto)

        btnRegistroDatosContacto!!.setOnClickListener(onClickListenerExterno)
    }
}