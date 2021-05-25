package com.JoexorArcofe.excoapp

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Ajustes: AppCompatActivity(), View.OnClickListener {

    private var btnAtras: ImageButton? = null
    private var btnLogOut: Button? = null
    private var btnConfigUser: Button? = null
    private var currentuser = Firebase.auth.currentUser
    private var username: String? = null
    var db = FirebaseFirestore.getInstance()
    private var tipo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajustes)

        btnAtras = findViewById<ImageButton>(R.id.atras)
        btnLogOut = findViewById<Button>(R.id.Logout)
        btnConfigUser = findViewById<Button>(R.id.ConfigUser)
        btnAtras!!.setOnClickListener(this)
        btnLogOut!!.setOnClickListener(this)
        btnConfigUser!!.setOnClickListener(this)

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
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atras -> {
                this.finish()
            }

            R.id.ConfigUser -> {
                val intent = Intent(this, User_Config::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }

            R.id.Logout -> {
                AuthUI.getInstance().signOut(this).addOnSuccessListener {
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(baseContext, "LOGOUT", Toast.LENGTH_LONG).show()
                    this.finish()
                }
            }
        }
    }
}