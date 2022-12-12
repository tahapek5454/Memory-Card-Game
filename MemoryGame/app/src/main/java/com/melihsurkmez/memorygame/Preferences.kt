package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.melihsurkmez.memorygame.databinding.ActivityPreferencesBinding

class Preferences : AppCompatActivity() {

    lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.birkisilik.setOnClickListener {



        }




    }
}