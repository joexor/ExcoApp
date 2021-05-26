package com.JoexorArcofe.excoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Resultats : AppCompatActivity(), View.OnClickListener {

    private var correcte: TextView? = null
    private var incorrecte: TextView? = null
    private var inicio: Button? = null
    private var reintentar: Button? = null
    private var ajustes: ImageButton? = null
    private var currentuser = Firebase.auth.currentUser
    private var username: String? = null
    private var puntuacion: String? = null

    var db = FirebaseFirestore.getInstance()
    private var tipo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultats)
        correcte =  findViewById<TextView>(R.id.NumAciertos)
        incorrecte =  findViewById<TextView>(R.id.NumFallos)
        inicio = findViewById<Button>(R.id.GoInici)
        reintentar = findViewById<Button>(R.id.Restart)
        ajustes =  findViewById<ImageButton>(R.id.ajustesResultats)

        var usuario = findViewById<TextView>(R.id.usuario)
        db.collection("usuarios").document(currentuser.email.toString())
                .get().addOnSuccessListener { document ->
                    if (document != null) {
                        username = document.getString("user")
                        usuario.text = username
                        //puntuacion = document.getString("puntuacion")
                    }
                }

        val i = intent

        val numCorrect = i.getStringExtra("correcte")
        val numWrong = i.getStringExtra("incorrecte")


        db.collection("ranking").document(currentuser.email.toString())
                .get().addOnSuccessListener { document ->
                    println("WEEEEEE")
                    if (document != null) {
                        println("Entra")
                        puntuacion = document.getString("puntuacion")
                        println("PUNTOS1:"  + puntuacion)
                        val suma = numCorrect?.toInt()?.let { puntuacion?.toInt()?.plus(it) }
                        println("SUMA:" + suma)
                        puntuacion = suma.toString()
                        println("PUNTOS2:" + puntuacion)
                        db.collection("ranking").document(currentuser.email.toString())
                                .update("puntuacion", puntuacion)
                    }
                }

        i.getStringExtra("type")?.let { tipo = it }
        correcte!!.setText(numCorrect)
        incorrecte!!.setText(numWrong)
        inicio!!.setOnClickListener(this)
        reintentar!!.setOnClickListener(this)
        ajustes!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val i = intent
        val tema = i.getStringExtra("Tema")
        when(v?.id){
            R.id.GoInici -> {
                val intent = Intent(this,Inici::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }

            R.id.Restart -> {
                val intent = Intent(this,Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("Tema",tema)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }

            R.id.ajustesResultats -> {
                val intent = Intent(this,Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
        }
    }
}