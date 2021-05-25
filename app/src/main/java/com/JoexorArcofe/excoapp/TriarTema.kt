package com.JoexorArcofe.excoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class TriarTema: AppCompatActivity(), View.OnClickListener {

    private var btnAtras: ImageView? = null
    private var btnAjustes: ImageButton? = null
    private var btnTema1: Button? = null
    private var btnTema2: Button? = null
    private var btnTema3: Button? = null
    private var currentuser = Firebase.auth.currentUser
    private var username: String? = null
    var db = FirebaseFirestore.getInstance()
    private var tipo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tema)
        //declaracion botones
        btnAtras = findViewById<ImageView>(R.id.atras)
        btnAjustes = findViewById<ImageButton>(R.id.ajustesTema)
        btnTema1 = findViewById<Button>(R.id.tema1)
        btnTema2 = findViewById<Button>(R.id.tema2)
        btnTema3 = findViewById<Button>(R.id.tema3)
        btnTema1!!.setOnClickListener(this)
        btnTema2!!.setOnClickListener(this)
        btnTema3!!.setOnClickListener(this)
        btnAtras!!.setOnClickListener(this)
        btnAjustes!!.setOnClickListener(this)

        intent.getStringExtra("type")?.let { tipo = it }

        /**
         * Part on canviem dinamicament un TextView
         */
        var usuario = findViewById<TextView>(R.id.usuario)
        db.collection("usuarios").document(currentuser.email.toString())
                .get().addOnSuccessListener { document ->
                    if (document != null) {
                        username = document.getString("user")
                        usuario.text = username
                    }
                }

        btnTema1!!.setText(Tema1)
        btnTema2!!.setText(Tema2)
        btnTema3!!.setText(Tema3)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atras -> {
                val intent = Intent(this, Inici::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", "google")
                startActivity(intent)
            }

            R.id.ajustesTema -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
            R.id.tema1 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Historia")
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
            R.id.tema2 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Entretenimiento")
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
            R.id.tema3 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Ciencia")
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val Tema1 = "Historia"
        const val Tema2 = "Entretenimiento"
        const val Tema3 = "Ciencia"
    }
}