package com.fime.lovetogo.fragments.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.fime.lovetogo.R

class BottomAppFragment : Fragment {

    var btnList: ImageView?=null
    var btnAdd: ImageView?=null
    var btnListEdit: ImageView?=null

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
        return inflater.inflate(R.layout.fragment_bottom_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnList = view.findViewById(R.id.btnList)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnListEdit = view.findViewById(R.id.btnListEdit)

        btnList!!.setOnClickListener(onClickListenerExterno)
        btnAdd!!.setOnClickListener(onClickListenerExterno)
        btnListEdit!!.setOnClickListener(onClickListenerExterno)
    }
}