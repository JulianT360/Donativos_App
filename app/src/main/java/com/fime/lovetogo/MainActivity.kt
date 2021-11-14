package com.fime.lovetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.fime.lovetogo.domain.structs.StructDireccion
import com.fime.lovetogo.domain.structs.StructRestaurante
import com.fime.lovetogo.fragments.register.HeaderRegisterFragment
import com.fime.lovetogo.fragments.register.InicioSesionFragment
import com.fime.lovetogo.fragments.register.RegistroContactoFragment
import com.fime.lovetogo.fragments.register.RegistroFragment
import com.fime.lovetogo.service.RestauranteService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var inicioSesionFragment: InicioSesionFragment?=null
    var registroFragment: RegistroFragment?=null
    var registroContactoFragment: RegistroContactoFragment?=null

    var headerRegisterFragment: HeaderRegisterFragment?=null

    private lateinit var auth: FirebaseAuth
    private lateinit var restauranteService: RestauranteService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        restauranteService = RestauranteService()

        inicioSesionFragment = InicioSesionFragment(this)
        registroFragment = RegistroFragment(this)
        registroContactoFragment = RegistroContactoFragment(this)
        headerRegisterFragment = HeaderRegisterFragment(this)

        var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.rootContainer, inicioSesionFragment!!)
        transaccion.show(inicioSesionFragment!!)
        transaccion.commit()
    }

    override fun onClick(v: View?) {

        when(v) {
            // Inicio de sesion
            inicioSesionFragment!!.btnInicioSesion -> {
                iniciarSesion()
            }
            // Registro de nueva cuenta
            inicioSesionFragment!!.btnRegistro -> {
                var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                transaccion.hide(inicioSesionFragment!!)
                transaccion.replace(R.id.headerRegisterApp, headerRegisterFragment!!)
                transaccion.replace(R.id.root, registroFragment!!)
                transaccion.show(headerRegisterFragment!!)
                transaccion.show(registroFragment!!)
                transaccion.commit()
            }
            // Registra cuenta de usuario y posteriormente lanza pantalla de datos de contacto
            registroFragment!!.btnRegistroContacto -> {
                crearCuenta()
            }
            // Creacion de cuenta
            registroContactoFragment!!.btnRegistroDatosContacto -> {
                registrarDatosContacto()
            }
            // Boton de back
            headerRegisterFragment!!.btnBack -> {
                var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                transaccion.hide(headerRegisterFragment!!)
                transaccion.hide(registroFragment!!)
                transaccion.hide(registroContactoFragment!!)
                transaccion.show(inicioSesionFragment!!)
                transaccion.commit()
            }
        }
    }

    private fun iniciarSesion() {
        val email = inicioSesionFragment!!.etEmailLogin!!.text.toString().trim()
        val password = inicioSesionFragment!!.etPasswordLogin!!.text.toString().trim()

        Log.d(TAG, "Inicio sesion: $email")
        // Validacion de formulario
        if(!formularioValido(email, password)) { return }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful) {
                    Log.d(TAG, "inicio de sesion exitoso")
                    val user = auth.currentUser

                    Log.d(TAG, "Verificado: ${user?.isEmailVerified}")
                    user?.let { validarCorreoVerificado(user.isEmailVerified) }
                } else {
                    Log.w(TAG, "Error al iniciar sesion", task.exception)
                    Toast.makeText(baseContext, "Credenciales incorrectas",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun crearCuenta() {
        val nombre = registroFragment!!.etNombreRestaurante!!.text.toString().trim()
        val email = registroFragment!!.etEmailRestaurante!!.text.toString().trim()
        val telefono = registroFragment!!.etTelefono!!.text.toString().trim()
        val password = registroFragment!!.etPwdRestaurante!!.text.toString().trim()
        val passwordConfirm = registroFragment!!.etPwdRestauranteConfirmacion!!.text.toString().trim()

        val restauranteRegistro = StructRestaurante(nombre, email, telefono, password, passwordConfirm)
        if(!this.formularioValido(restauranteRegistro)) { return }

        auth.createUserWithEmailAndPassword(restauranteRegistro.email, restauranteRegistro.password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, task.isSuccessful.toString())
                if (task.isSuccessful) {
                    Log.d(TAG, "crearUsuarioCorreoYContraseña:Ok")
                    val user = auth.currentUser!!
                    restauranteService.registrar(restauranteRegistro, user)

                    var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                    transaccion.replace(R.id.root, registroContactoFragment!!)
                    transaccion.show(registroContactoFragment!!)
                    transaccion.commit()

                } else {
                    Log.w(TAG, "crearUsuarioCorreoYContraseña:error", task.exception)
                    showToast("Ocurrió un problema al registrar el usuario, intente de nuevo más tarde.")
                }
            }
    }

    private fun registrarDatosContacto() {

        val calle = registroContactoFragment!!.etCalle!!.text.toString()
        val numeroExterior = registroContactoFragment!!.etNumeroExterior!!.text.toString()
        val numeroInterior = registroContactoFragment!!.etNumeroInterior!!.text.toString()
        val colonia = registroContactoFragment!!.etColonia!!.text.toString()
        val codigoPostal = registroContactoFragment!!.codigoPostal!!.text.toString().trim()
        val municipio = registroContactoFragment!!.etMunicipio!!.text.toString()
        val estado = registroContactoFragment!!.etEstado!!.text.toString()

        val direccionRestauranteRegistro = StructDireccion(calle, numeroExterior,
            numeroInterior, colonia, codigoPostal, municipio, estado)
        if(!this.formularioContactoValido(direccionRestauranteRegistro)) { return }

        val user = auth.currentUser!!
        restauranteService.registrarDireccion(direccionRestauranteRegistro, user.uid)

        enviarCorreoVerificacion()
    }

    private fun enviarCorreoVerificacion() {
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "sendedEmailVerification")
                    var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                    transaccion.hide(headerRegisterFragment!!)
                    transaccion.hide(registroFragment!!)
                    transaccion.hide(registroContactoFragment!!)
                    transaccion.replace(R.id.rootContainer, inicioSesionFragment!!)
                    transaccion.show(inicioSesionFragment!!)
                    transaccion.commit()
                    showToast("Se ha enviado un correo electrónico de verificación.")
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    showToast("Ocurrió un error al enviar el correo de verificación.")
                }
            }
    }

    private fun validarCorreoVerificado(emailVerificado: Boolean) {
        if(!emailVerificado) {
            Toast.makeText(baseContext, "Debe verificar su correo primero",
                Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, RestauranteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    //Metodo que valida los campos de registro principales
    private fun formularioValido(registro: StructRestaurante): Boolean {
        var valid = true;

        val nombreRev = registro.nombre
        if (TextUtils.isEmpty(nombreRev)) {
            registroFragment!!.etNombreRestaurante!!.error = "Ingrese un nombre."
            valid = false
        }

        val emailRev = registro.email
        if (TextUtils.isEmpty(emailRev)) {
            registroFragment!!.etEmailRestaurante!!.error = "Ingrese un correo eléctronico."
            valid = false
        }

        val passRev = registro.password
        if (TextUtils.isEmpty(passRev)) {
            registroFragment!!.etPwdRestaurante!!.error = "Ingrese una contraseña."
            valid = false
        }

        val confPassRev = registro.passwordConfirm
        if (TextUtils.isEmpty(confPassRev)) {
            registroFragment!!.etPwdRestauranteConfirmacion!!.error = "Debe confirmar la contraseña."
            valid = false
        }

        if (!passRev.equals(confPassRev)) {
            Toast.makeText(baseContext, "Las contraseñas no coinciden.",
                Toast.LENGTH_SHORT).show()
            valid = false
        }

        return valid
    }

    // Metodo que valida los campos de contacto para el registro
    private fun formularioContactoValido(registro: StructDireccion): Boolean {
        var valid = true;

        val calleRev = registro.calle
        if (TextUtils.isEmpty(calleRev)) {
            registroContactoFragment!!.etCalle!!.error = "Ingrese una calle válida."
            valid = false
        }

        val numeroExteriorRev = registro.numeroExterior
        if (TextUtils.isEmpty(numeroExteriorRev)) {
            registroContactoFragment!!.etNumeroExterior!!.error = "Ingrese un número exterior."
            valid = false
        }

        val coloniaRev = registro.colonia
        if (TextUtils.isEmpty(coloniaRev)) {
            registroContactoFragment!!.etColonia!!.error = "Ingrese una colonia válida."
            valid = false
        }

        val codigoPostalRev = registro.codigoPostal
        if (TextUtils.isEmpty(codigoPostalRev)) {
            registroContactoFragment!!.codigoPostal!!.error = "Ingrese el código postal correspondiente."
            valid = false
        }

        val municipioRev = registro.municipio
        if (TextUtils.isEmpty(municipioRev)) {
            registroContactoFragment!!.etMunicipio!!.error = "Ingrese un municipio válido."
            valid = false
        }

        val estadoRev = registro.estado
        if (TextUtils.isEmpty(estadoRev)) {
            registroContactoFragment!!.etEstado!!.error = "Ingrese un Estado válido."
            valid = false
        }

        return valid
    }


    private fun formularioValido(emailRev:String, passRev: String): Boolean {
        var valid = true;

        if (TextUtils.isEmpty(emailRev)) {
            inicioSesionFragment!!.etEmailLogin!!.error = "Ingrese un correo eléctronico."
            valid = false
        }

        if (TextUtils.isEmpty(passRev)) {
            inicioSesionFragment!!.etPasswordLogin!!.error = "Ingrese una contraseña."
            valid = false
        }

        return valid
    }
    companion object {
        private const val TAG = "MainActivity"
    }
}