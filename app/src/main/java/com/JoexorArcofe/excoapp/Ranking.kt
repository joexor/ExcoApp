package com.JoexorArcofe.excoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Ranking: AppCompatActivity(), View.OnClickListener {

    private var btnAjustes: ImageButton? = null
    private var btnAtras: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ranking)

        btnAjustes = findViewById<ImageButton>(R.id.ajustesRanking)
        btnAjustes = findViewById<ImageButton>(R.id.ajustesTema)
        btnAjustes!!.setOnClickListener(this)
        btnAjustes!!.setOnClickListener(this)


        var currentuser = Firebase.auth.currentUser
        var usuario = findViewById<TextView>(R.id.usuario)
        val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.atras -> {
                val intent = Intent(this, Inici::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            R.id.ajustesRanking -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}