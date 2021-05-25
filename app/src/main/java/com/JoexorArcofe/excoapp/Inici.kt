package com.JoexorArcofe.excoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Inici: AppCompatActivity(), View.OnClickListener {
    enum class ProviderType{
        BASIC
    }

    private var btnAjustes: ImageButton? = null
    private var btnJugar: Button? = null
    private var btnRanking: Button? = null
    private var currentuser = Firebase.auth.currentUser
    private var username: String? = null
    var db = FirebaseFirestore.getInstance()
    private var tipo = ""

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.inici)

            btnAjustes = findViewById<ImageButton>(R.id.ajustesInici)
            btnJugar = findViewById<Button>(R.id.jugar)
            btnRanking = findViewById<Button>(R.id.ranking)
            btnAjustes!!.setOnClickListener(this)
            btnJugar!!.setOnClickListener(this)
            btnRanking!!.setOnClickListener(this)

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
        when(v?.id){
            R.id.ajustesInici -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }

            R.id.jugar -> {
                val intent = Intent(this, TriarTema::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }

            R.id.ranking -> {
                val intent = Intent(this, Ranking::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("type", tipo)
                startActivity(intent)
            }
        }
    }
}