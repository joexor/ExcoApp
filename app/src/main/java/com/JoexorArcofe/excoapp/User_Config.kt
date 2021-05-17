package com.JoexorArcofe.excoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Modifier

class User_Config : AppCompatActivity(), View.OnClickListener {

    private var btnAtras: ImageButton? = null
    private var textEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_config)

        btnAtras = findViewById<ImageButton>(R.id.atras)
        textEmail = findViewById<EditText>(R.id.email)

        var currentuser = Firebase.auth.currentUser
        var usuario = findViewById<TextView>(R.id.usuario)
        val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)

        btnAtras!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atras -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }
}


