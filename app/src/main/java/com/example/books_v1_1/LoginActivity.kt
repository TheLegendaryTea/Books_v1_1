package com.example.log

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.books_v1_1.HomeActivity
import com.example.books_v1_1.MainActivity
import com.example.books_v1_1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        // Initialize Firebase Auth
        auth = Firebase.auth

        progressDialog = ProgressDialog(this,)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Loggin in...")
        progressDialog.setCanceledOnTouchOutside(false)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registertext: TextView =findViewById(R.id.register_now)
        registertext.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        val loginButton:Button = findViewById(R.id.login)
        loginButton.setOnClickListener{
            performSignIn()
        }
    }

    private fun performSignIn() {
        progressDialog.show()
        val email: EditText = findViewById<EditText>(R.id.username)
        val password:EditText = findViewById<EditText>(R.id.password)

        if (email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT)
                .show()
            progressDialog.dismiss()
            return
        }
        val inputEmail = email.text.toString()
        val inputPassword = password.text.toString()
        auth.signInWithEmailAndPassword(inputEmail, inputPassword)
            .addOnSuccessListener {
                progressDialog.dismiss()
                val firebaseUser = auth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this,"LogIn as $email",Toast.LENGTH_LONG).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()

            }
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    val intent=Intent(this,MainActivity::class.java)
//                    startActivity(intent)

                    Toast.makeText(baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT).show()


                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(this,"Error occurred${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }
    private fun checkUser(){
        val firebaseUser = auth.currentUser
        if (firebaseUser !=null)
            startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}