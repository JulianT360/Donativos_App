package com.fime.lovetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContentView(R.layout.activity_login)

    }

    override fun onStart() {
        super.onStart()

        // Redirige a la pantalla de registro
        val btnRegister = findViewById<TextView>(R.id.btnRegister)
        btnRegister.setOnClickListener{
            val intent = Intent(this, SelectTypeRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener{
            iniciarSesion()
        }
    }

    fun iniciarSesion() {
        val email = findViewById<EditText>(R.id.txtUsrEmail)
        val password = findViewById<EditText>(R.id.txtUsrPwd)
        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Ha iniciado sesión correctamente con " + user?.email.toString(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Occurrió un error al iniciar sesión.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }


}