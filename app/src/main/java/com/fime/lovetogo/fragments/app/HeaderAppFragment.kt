package com.fime.lovetogo.fragments.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fime.lovetogo.R

class HeaderAppFragment : Fragment {

    var btnHome: ImageView?=null
    var btnLogout: ImageView?=null

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
        return inflater.inflate(R.layout.fragment_header_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHome = view.findViewById(R.id.btnHome)
        btnLogout = view.findViewById(R.id.btnLogout)

        btnHome!!.setOnClickListener(onClickListenerExterno)
        btnLogout!!.setOnClickListener(onClickListenerExterno)
    }
}