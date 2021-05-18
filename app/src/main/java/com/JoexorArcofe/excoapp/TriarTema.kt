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
import com.google.firebase.ktx.Firebase

class TriarTema: AppCompatActivity(), View.OnClickListener {

    private var btnAtras: ImageView? = null
    private var btnAjustes: ImageButton? = null
    private var btnTema1: Button? = null
    private var btnTema2: Button? = null
    private var btnTema3: Button? = null

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

        /**
         * Part on canviem dinamicament un TextView
         */
        var currentuser = Firebase.auth.currentUser
        var usuario = findViewById<TextView>(R.id.usuario)
        val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)

        btnTema1!!.setText(Tema1)
        btnTema2!!.setText(Tema2)
        btnTema3!!.setText(Tema3)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atras -> {
                val intent = Intent(this, Inici::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

            R.id.ajustesTema -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            R.id.tema1 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Historia")
                startActivity(intent)
            }
            R.id.tema2 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Entretenimiento")
                startActivity(intent)
            }
            R.id.tema3 -> {
                val intent = Intent(this, Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema", "Ciencia")
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