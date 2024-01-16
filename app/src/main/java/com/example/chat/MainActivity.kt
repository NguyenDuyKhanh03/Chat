package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.chat.Adapter.FragmentsAdapter
import com.example.chat.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var bingding:ActivityMainBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bingding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(bingding.root)
        auth=FirebaseAuth.getInstance()
        bingding.topAppBar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.settings-> {
                    Toast.makeText(this,"Setting is clicked",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.groupChat-> {
                    Toast.makeText(this,"Group chat is started",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout-> {
                    auth.signOut()
                    val intent= Intent(this,SignInActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
                    true
                }
                else->
                    false
            }
        }
        bingding.viewPager.adapter=FragmentsAdapter(supportFragmentManager)
        Toast.makeText(this,bingding.viewPager.currentItem.toString(),Toast.LENGTH_LONG).show()
        bingding.tabLayout.setupWithViewPager(bingding.viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d("Menu", "onCreateOptionsMenu is called.")
        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("Menu", "onOptionsItemSelected is called.")
        when(item.itemId){
            R.id.settings-> {
                Toast.makeText(this,"Setting is clicked",Toast.LENGTH_SHORT).show()
            }
            R.id.groupChat-> {
                Toast.makeText(this,"Group chat is started",Toast.LENGTH_SHORT).show()
            }
            R.id.logout-> {
                auth.signOut()
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }



}

