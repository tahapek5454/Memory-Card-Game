package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.melihsurkmez.memorygame.databinding.ActivityPreferencesBinding

class Preferences : AppCompatActivity() {
    var oyuncusayisi: RadioGroup? = null
    var zorluk: RadioGroup? = null
    lateinit var radioButton: RadioButton
    lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oyuncusayisi = binding.oyuncusayisi
        zorluk = binding.zorlukderecesigroup

        binding.oyunbutonu.setOnClickListener {

            val selectedOption: Int = oyuncusayisi!!.checkedRadioButtonId

            

            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this@Preferences,radioButton.text, Toast.LENGTH_LONG).show()
            }








        }







    }
}