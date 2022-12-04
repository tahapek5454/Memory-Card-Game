package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melihsurkmez.memorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



    }
}