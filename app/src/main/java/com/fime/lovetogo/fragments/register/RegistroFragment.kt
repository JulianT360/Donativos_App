package com.fime.lovetogo.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.fime.lovetogo.R

class RegistroFragment : Fragment {

    var etNombreRestaurante: EditText?=null
    var etEmailRestaurante: EditText?=null
    var etPwdRestaurante: EditText?=null
    var etPwdRestauranteConfirmacion: EditText?=null
    var etTelefono: EditText?=null

    var btnRegistroContacto: Button?=null

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
        return inflater.inflate(R.layout.fragment_registro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNombreRestaurante = view.findViewById(R.id.etNombreAlimento)
        etEmailRestaurante = view.findViewById(R.id.etCaducidadAlimento)
        etTelefono = view.findViewById(R.id.etCantidadAlimento)
        etPwdRestaurante = view.findViewById(R.id.etCategoriaAlimento)
        etPwdRestauranteConfirmacion = view.findViewById(R.id.etPwdRestauranteConfirmacion)
        btnRegistroContacto = view.findViewById(R.id.btnAgregarAlimento)

        btnRegistroContacto!!.setOnClickListener(onClickListenerExterno)
    }
}