package com.example.chat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.databinding.ActivitySignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    private lateinit var bingding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(bingding.root)
        auth=Firebase.auth
        database=FirebaseDatabase.getInstance()
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Login")
        progressDialog.setMessage("Please Wait,\n,Validation in Progress")

        bingding.btnSignIn.setOnClickListener {
            if(!bingding.txtEmail.text.toString().isEmpty().toString().isEmpty() &&!bingding.txtPassword.text.toString().isEmpty())
            {
                progressDialog.show()
                auth.signInWithEmailAndPassword(bingding.txtEmail.text.toString() ,bingding.txtPassword.text.toString())
                    .addOnCompleteListener(this){ task->
                        progressDialog.dismiss()
                        if(task.isSuccessful)
                        {
                            val intend=Intent(this,MainActivity::class.java)
                            startActivity(intend)

                        }
                        else
                        {
                            Toast.makeText(this, task.exception!!.message,Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            else{
                Toast.makeText(this,"Enter Credentials",Toast.LENGTH_SHORT).show()
            }
        }

        if(auth.currentUser!=null)
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        bingding.txtClickSignUp.setOnClickListener{
            val intent=Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            
        }
    }
}