package com.JoexorArcofe.excoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Registre: AppCompatActivity(), View.OnClickListener {

    private var btnRegistre: Button? = null
    private var btnVolverInicio: Button? = null
    private var user: EditText? = null
    private var password: EditText? = null
    private var repeatPwd: EditText? = null
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registre)
        //declaracion botones
        btnVolverInicio = findViewById<Button>(R.id.btnBackLogIn)
        btnRegistre = findViewById<Button>(R.id.btnregistre)
        btnVolverInicio!!.setOnClickListener(this)
        btnRegistre!!.setOnClickListener(this)

        // Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("Message","Integraci√≥ de Firebase completada")
        analytics.logEvent("InitScreen",bundle)

        //Setup
        setup()
    }

    private fun setDataFromTexBox()
    {
        user = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editTextNumberPassword)
        repeatPwd = findViewById(R.id.repeatPassword)
    }

    private fun setup() {
        title = "Autenticaci√≥"
        /**
         * Part per fer el Registre d'un usuari
         */
        btnRegistre?.setOnClickListener{
            setDataFromTexBox()
            if (user?.text?.isNotEmpty()!! && password?.text?.isNotEmpty()!!){
                if(password?.text?.toString().equals(repeatPwd?.text?.toString())!!) {
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user!!.text.toString(),password!!.text.toString()).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val apodo = user?.text?.split("@")
                                    val usuario = hashMapOf(
                                            "user" to apodo?.get(0),
                                            "email" to user?.text.toString(),
                                            "puntuacion" to 0
                                    )

                                    val rankingUser = hashMapOf(
                                            "user" to apodo?.get(0),
                                            "puntuacion" to 0
                                    )
                                    db.collection("usuarios").document(user?.text.toString())
                                            .set(usuario)
                                    db.collection("ranking").document(user?.text.toString())
                                            .set(rankingUser)
                                    showHome(it.result?.user?.email ?: "", Inici.ProviderType.BASIC)
                                } else {
                                    var mensaje = "S'ha produ√Įt un error al registrar l'usuari"
                                    showAlert(mensaje)
                                }

                            }
                }else{
                    var mensaje = "La contrase√Īa no coincide"
                    showAlert(mensaje)
                }
            }else{
                var mensaje = "Los campos de usuario i/o contrase√Īa estan vacios"
                showAlert(mensaje)
            }
        }
    }

    /**
     * Classe d'alerta per quan tenim un error al registar-se
     */
    private fun showAlert(mensaje: String) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    /**
     * Metode que introdueix usuaris per poder iniciar sessi√≥, la quall guardara el usuari que sera el email i el password
     */
    private fun showHome(user: String, password: Inici.ProviderType){

        val homeIntent  = Intent(this, Inici::class.java).apply {
            putExtra("user",user)
            putExtra("password",password.name)
            putExtra("type","email")
        }
        startActivity(homeIntent)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBackLogIn ->{
                this.finish()
            }
        }
    }
}