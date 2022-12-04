package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melihsurkmez.memorygame.databinding.ActivityChangePasswordBinding

class ChangePassword : AppCompatActivity() {
    lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sifreButonu.setOnClickListener {

            intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)

        }



    }
}