package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.melihsurkmez.memorygame.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Write a message to the database


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.kaydol.setOnClickListener {

            intent = Intent(applicationContext,Register::class.java)
            startActivity(intent)
        }

        binding.sifreUnut.setOnClickListener {

            intent= Intent(applicationContext,ChangePassword::class.java)
            startActivity(intent)


        }
        giris_butonu.setOnClickListener{

            intent = Intent(applicationContext, Game::class.java)
            startActivity(intent)
        }



    }
}