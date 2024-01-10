package com.example.chat

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Toast
import com.example.chat.Models.Users
import com.example.chat.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var bingding:ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(bingding.root)
        auth = Firebase.auth
        database=FirebaseDatabase.getInstance()
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Creating Account")
        progressDialog.setMessage("We're creating your account")

        bingding.btnSignUp.setOnClickListener {
            if(!bingding.txtUserName.text.toString().isEmpty() &&!bingding.txtEmail.text.toString().isEmpty().toString().isEmpty() &&!bingding.txtPassword.text.toString().isEmpty() )
            {
                progressDialog.show()
                auth.createUserWithEmailAndPassword(bingding.txtEmail.text.toString(),bingding.txtPassword.text.toString())
                    .addOnCompleteListener(this){ task ->
                        progressDialog.dismiss()
                        if (task.isSuccessful) {
                            val users=Users(userName =  bingding.txtUserName.text.toString(), mail =  bingding.txtEmail.text.toString(), password =  bingding.txtPassword.text.toString())
                            val userId:String=task.result.user!!.uid
//                            database.reference.child("Users")
                            database.reference.child("Users").child(userId).setValue(users)
                            Toast.makeText(this,"Sign up Successful",Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()

                        }

                    }


            }
            else
            {
                Toast.makeText(this,"Enter Credentials",Toast.LENGTH_SHORT).show()
            }
        }
        bingding.txtAlreadyHaveAccount.setOnClickListener{
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }
}