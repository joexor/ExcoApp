package com.JoexorArcofe.excoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Resultats : AppCompatActivity(), View.OnClickListener {

    private var correcte: TextView? = null
    private var incorrecte: TextView? = null
    private var inicio: Button? = null
    private var reintentar: Button? = null
    private var ajustes: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultats)

        correcte =  findViewById<TextView>(R.id.NumAciertos)
        incorrecte =  findViewById<TextView>(R.id.NumFallos)
        inicio = findViewById<Button>(R.id.GoInici)
        reintentar = findViewById<Button>(R.id.Restart)
        ajustes =  findViewById<ImageButton>(R.id.ajustesResultats)

        var currentuser = Firebase.auth.currentUser
        var usuario = findViewById<TextView>(R.id.usuario)
        val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)

        val i = intent

        val numCorrect = i.getStringExtra("correcte")
        val numWrong = i.getStringExtra("incorrecte")

        correcte!!.setText(numCorrect)
        incorrecte!!.setText(numWrong)
        inicio!!.setOnClickListener(this)
        reintentar!!.setOnClickListener(this)
        ajustes!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.GoInici -> {
                val intent = Intent(this,Inici::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

            R.id.Restart -> {
                val intent = Intent(this,Quiz::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }

            R.id.ajustesResultats -> {
                val intent = Intent(this,Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }


}