package com.fime.lovetogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.fime.lovetogo.domain.structs.StructAlimento
import com.fime.lovetogo.fragments.app.BottomAppFragment
import com.fime.lovetogo.fragments.app.HeaderAppFragment
import com.fime.lovetogo.fragments.app.restaurant.AgregarPlatilloFragment
import com.fime.lovetogo.fragments.app.restaurant.DetalleArticuloFragment
import com.fime.lovetogo.fragments.app.restaurant.ListaAlimentosFragment
import com.fime.lovetogo.service.RestauranteService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RestauranteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var restauranteService: RestauranteService

    var listaAlimentos: ArrayList<StructAlimento>?=null

    var agregarAlimentoFragment: AgregarPlatilloFragment?=null
    var headerAppFragment: HeaderAppFragment?=null
    var bottomAppFragment: BottomAppFragment?=null
    var listaAlimentosFragment: ListaAlimentosFragment?=null
    var detalleAlimentosFragment: DetalleArticuloFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurante)

        auth = Firebase.auth
        restauranteService = RestauranteService()

        headerAppFragment = HeaderAppFragment(this)
        bottomAppFragment = BottomAppFragment(this)
        agregarAlimentoFragment = AgregarPlatilloFragment(this)
        listaAlimentosFragment = ListaAlimentosFragment(this)
        detalleAlimentosFragment = DetalleArticuloFragment(this)

        val transaccion: FragmentTransaction =this.supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.headerApp, headerAppFragment!!)
        transaccion.replace(R.id.bottomApp, bottomAppFragment!!)
        transaccion.replace(R.id.Container, listaAlimentosFragment!!)
        transaccion.commit()
    }

    override fun onClick(v: View?) {

        when(v) {
            // Boton para cerrar sesion
            headerAppFragment!!.btnLogout -> {
                signOut()
            }
            // Boton de inicio
            headerAppFragment!!.btnHome -> {
                var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                transaccion.replace(R.id.Container, listaAlimentosFragment!!)
                transaccion.commit()
            }
            // Cambiar a pantalla para agregar alimento
            bottomAppFragment!!.btnAdd -> {
                var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                transaccion.replace(R.id.Container, agregarAlimentoFragment!!)
                transaccion.commit()
            }
            // Boton para agregar alimentos
            agregarAlimentoFragment!!.btnAgregar -> {
                agregarAlimento()
            }
            // Listar alimentos
            bottomAppFragment!!.btnList -> {
                listarAlimentos()
            }
            bottomAppFragment!!.btnListEdit -> {
                var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
                transaccion.replace(R.id.Container, detalleAlimentosFragment!!)
                transaccion.commit()
            }
        }
    }

    private fun listarAlimentos() {
        var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.Container, listaAlimentosFragment!!)
        transaccion.commit()
    }

    private fun agregarAlimento() {
        val nombre = agregarAlimentoFragment!!.etNombre!!.text.toString()
        val caducidad = agregarAlimentoFragment!!.etCaducidad!!.text.toString()
        val cantidad = agregarAlimentoFragment!!.etCantidad!!.text.toString()

        if (TextUtils.isEmpty(cantidad)) {
            agregarAlimentoFragment!!.etCantidad!!.error = "Ingrese una cantidad valida."
            return
        }

        val user = auth.currentUser!!
        val alimento = StructAlimento(nombre, cantidad.toInt(), caducidad)

        if(!formularioValido(alimento)) { return }

        restauranteService.registrarPlatillo(alimento, user.uid)
        showToast("Se ha registrado el alimento correctamente.")
        limpiarCampos()

        var transaccion: FragmentTransaction=this.supportFragmentManager.beginTransaction()
        transaccion.replace(R.id.Container, listaAlimentosFragment!!)
        transaccion.commit()
    }

    private fun signOut() {
        auth.signOut()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        showToast("Ha cerrado su sesion exitosamente.")
    }

    private fun formularioValido(registro: StructAlimento): Boolean {
        var valid = true;

        val nombreRev = registro.nombre
        if (TextUtils.isEmpty(nombreRev)) {
            agregarAlimentoFragment!!.etNombre!!.error = "Ingrese un nombre válido."
            valid = false
        }

        val cantidadRev = registro.cantidad
        if (cantidadRev == null) {
            agregarAlimentoFragment!!.etCantidad!!.error = "Ingrese una cantidad válida."
            valid = false
        }

        val caducidadRev = registro.caducidad
        if (TextUtils.isEmpty(caducidadRev)) {
            agregarAlimentoFragment!!.etCaducidad!!.error = "Ingrese una fecha de caducidad válida."
            valid = false
        }

        return valid
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        agregarAlimentoFragment!!.etNombre!!.text.clear()
        agregarAlimentoFragment!!.etCantidad!!.text.clear()
        agregarAlimentoFragment!!.etCaducidad!!.text.clear()
    }

    companion object {
        private const val TAG = "RestauranteActivity"
    }
}