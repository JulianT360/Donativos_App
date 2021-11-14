package com.fime.lovetogo.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.fime.lovetogo.R

class InicioSesionFragment : Fragment {

    var etEmailLogin: EditText?=null
    var etPasswordLogin: EditText?=null

    var btnInicioSesion: Button?=null
    var btnRegistro: TextView?=null

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
        return inflater.inflate(R.layout.fragment_inicio_sesion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmailLogin = view.findViewById(R.id.etEmailLogin)
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin)
        btnInicioSesion = view.findViewById(R.id.btnInicioSesion)
        btnRegistro = view.findViewById(R.id.btnRegistro)

        btnInicioSesion!!.setOnClickListener(onClickListenerExterno)
        btnRegistro!!.setOnClickListener(onClickListenerExterno)
    }
}