package com.JoexorArcofe.excoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class User_Config : AppCompatActivity(), View.OnClickListener {

    private var btnAtras: ImageButton? = null
    private var btnModificar: Button? = null
    private var textEmail: EditText? = null
    private var textUser: EditText? = null
    private var password: EditText? = null
    private var repeatPwd: EditText? = null
    private var currentuser = Firebase.auth.currentUser
    private var username: String? = null
    private var correo: String? = null
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_config)

        btnAtras = findViewById<ImageButton>(R.id.atras)
        btnModificar = findViewById<Button>(R.id.modificar)
        textEmail = findViewById<EditText>(R.id.email)
        textUser = findViewById<EditText>(R.id.user)
        password = findViewById<EditText>(R.id.password)
        repeatPwd = findViewById<EditText>(R.id.repeatPassword)

        Firebase.auth

        var usuario = findViewById<TextView>(R.id.usuario)
        /*val user = currentuser?.email?.split('@')
        usuario.text = user?.get(0)*/

        btnAtras!!.setOnClickListener(this)
        btnModificar!!.setOnClickListener(this)

        db.collection("usuarios").document(currentuser.email.toString())
                .get().addOnSuccessListener { document ->
                    if (document != null) {
                        username = document.getString("user")
                        correo = document.getString("email")
                        textEmail!!.setText(correo)
                        textUser!!.setText(username)
                        usuario.text = username
                        Log.d("", "DocumentSnapshot data: ${document.data}")
                    } else {
                        Log.d("", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("", "get failed with ", exception)
                }

        println("CORREO:" + correo)
        println("USUARIO:" + username)
        /*textEmail!!.setText(correo)
        textUser!!.setText(username)*/
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.atras -> {
                val intent = Intent(this, Ajustes::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
            R.id.modificar -> {
                if (textEmail?.text?.isNotEmpty()!!  && password?.text?.isNotEmpty()!!) {
                    if (password?.text?.toString().equals(repeatPwd?.text?.toString())!!) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(textEmail?.text.toString(),
                                password?.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                db.collection("usuarios").document(currentuser.email.toString())
                                        .update("user", textUser?.text?.toString())
                                        .addOnSuccessListener { Log.d("", "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w("", "Error updating document", e) }
                            } else {
                                val builder = AlertDialog.Builder(this)
                                builder.setTitle("Error")
                                builder.setMessage("Contraseña Incorrecta")
                                builder.setPositiveButton("Aceptar", null)
                                val dialog: AlertDialog = builder.create()
                                dialog.show()
                            }
                        }
                    }else{
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Error")
                        builder.setMessage("La contraseña no coincide")
                        builder.setPositiveButton("Aceptar",null)
                        val dialog: AlertDialog = builder.create()
                        dialog.show()
                    }
                }else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("Los campos de usuario i/o contraseña estan vacios")
                    builder.setPositiveButton("Aceptar",null)
                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
        }
    }


}


