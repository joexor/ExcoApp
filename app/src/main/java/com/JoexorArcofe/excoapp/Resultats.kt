package com.JoexorArcofe.excoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Resultats : AppCompatActivity() {

    private var correcte: TextView? = null
    private var incorrecte: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultats)

        correcte =  findViewById<TextView>(R.id.NumAciertos)
        incorrecte =  findViewById<TextView>(R.id.NumFallos)

        var currentuser = Firebase.auth.currentUser
        var usuario = findViewById<TextView>(R.id.usuario)
        val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)

        val i = intent

        val numCorrect = i.getStringExtra("correcte")
        val numWrong = i.getStringExtra("incorrecte")

        correcte!!.setText(numCorrect)
        incorrecte!!.setText(numWrong)
    }


}