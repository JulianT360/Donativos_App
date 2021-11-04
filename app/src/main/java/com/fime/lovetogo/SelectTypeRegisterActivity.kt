package com.fime.lovetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SelectTypeRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_type_register)
    }

    override fun onStart() {
        super.onStart()

        // Boton de regreso
        val btnUndo = findViewById<ImageView>(R.id.btnBack)
        btnUndo.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Boton de registro de usuario
        val btnUsrReg = findViewById<Button>(R.id.btnUserRegister)
        btnUsrReg.setOnClickListener{
            val intent = Intent(this, AssociationRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Boton de registro de restaurante
        val btnResReg = findViewById<Button>(R.id.btnRestaurantRegister)
        btnResReg.setOnClickListener{
            val intent = Intent(this, RestaurantRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}