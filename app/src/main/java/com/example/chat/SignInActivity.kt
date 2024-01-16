package com.example.chat

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat.Models.Users
import com.example.chat.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {
    private lateinit var bingding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mGoogleSignInClient:GoogleSignInClient


    //RC_SIGN là mã yêu cầu bạn sẽ chỉ định để bắt đầu hoạt động mới.
    // Đây có thể là bất kỳ số nào. Khi người dùng hoàn tất hoạt động tiếp theo và quay lại,
    // hệ thống sẽ gọi phương thức onActivityResult() của activity bạn
    private val RC_SIGN_IN=30
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        bingding.btnGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account!!.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user=auth.currentUser
                    val userName=user!!.displayName.toString()
                    val mail=user.email.toString()
                    val users=Users(userName=userName,mail=mail)
                    users.setProfilePic(user.photoUrl.toString())
                    database.reference.child("Users").child(user.uid).setValue(users)
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Sign in with Google", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}