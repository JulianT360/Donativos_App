package com.fime.lovetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AssociationRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_association_register)
    }

    override fun onStart() {
        super.onStart()

        // Boton de regreso
        val btnUndo = findViewById<ImageView>(R.id.btnBack)
        btnUndo.setOnClickListener{
            val intent = Intent(this, RestauranteActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Boton de registro de usuario
        val btnAssociationReg = findViewById<Button>(R.id.btnAssociationRegAcc)
        btnAssociationReg.setOnClickListener{
            registroAsociacion()
        }
    }

    fun registroAsociacion() {
        val email = findViewById<EditText>(R.id.txtAssociationEmail)
        val password = findViewById<EditText>(R.id.txtAssociationPassword)
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                val intent = Intent(this, RestauranteActivity::class.java)
                startActivity(intent)
                if(task.isSuccessful) {
                    Toast.makeText(baseContext, "Su cuenta se ha registrado correctamente.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Ocurrió un error al registrar la cuenta.",
                        Toast.LENGTH_SHORT).show()
                }
                finish()
            }
    }

}