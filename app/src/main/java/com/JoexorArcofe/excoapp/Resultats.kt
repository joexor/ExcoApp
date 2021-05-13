package com.JoexorArcofe.excoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Resultats : AppCompatActivity() {

    private var correcte: TextView? = null
    private var incorrecte: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultats)

        correcte =  findViewById<TextView>(R.id.NumAciertos)
        incorrecte =  findViewById<TextView>(R.id.NumFallos)

        val i = intent

        val numCorrect = i.getStringExtra("correcte")
        val numWrong = i.getStringExtra("incorrecte")

        correcte!!.setText(numCorrect)
        incorrecte!!.setText(numWrong)
    }


}