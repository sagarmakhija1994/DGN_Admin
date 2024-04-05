package com.sagarmakhija1994.dgnadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonObject
import com.sagarmakhija1994.dgnadmin.databinding.ActivityMainBinding
import com.sagarmakhija1994.dgnadmin.ui.home.HomeActivity
import com.sagarmakhija1994.dgnadmin.ui.login.LoginActivity
import com.sagarmakhija1994.dgnadmin.util.AppLevelData

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        getAdminPin()
    }

    private fun getAdminPin(){
        try {
            db.collection("Admin")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        AppLevelData.adminPin = document.data["pin"].toString()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        this,
                        "Failed to connect, Please check your internet connection and try again!",
                        LENGTH_LONG
                    ).show()
                }
        }catch (ignore:Exception){
            Toast.makeText(
                this,
                "Failed to connect, Please check your internet connection and try again!",
                LENGTH_LONG
            ).show()
        }
    }
}