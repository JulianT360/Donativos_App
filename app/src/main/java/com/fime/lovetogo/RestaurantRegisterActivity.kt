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

class RestaurantRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_restaurant_register)
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

        val btnResReg = findViewById<Button>(R.id.btnResCreateAcc)
        btnResReg.setOnClickListener{
            registroRestaurante()
        }
    }

    fun registroRestaurante() {
        val email = findViewById<EditText>(R.id.txtResEmail)
        val password = findViewById<EditText>(R.id.txtResPassword)
        auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                val intent = Intent(this, LoginActivity::class.java)
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