package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageButton
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    //Butonlarimizin listesi burada olucak
    private lateinit var buttons: List<ImageButton>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)



        buttons = listOf(binding.imageButton,binding.imageButton2,binding.imageButton3,binding.imageButton4,binding.imageButton5,binding.imageButton6,binding.imageButton7,binding.imageButton8
        ,binding.imageButton9,binding.imageButton10,binding.imageButton11,binding.imageButton12,binding.imageButton13,binding.imageButton14,binding.imageButton15,binding.imageButton16)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                button.setImageResource(R.drawable.ic_baseline_email_24)
            }
        }

        myTimer()
    }





    //Timer
    private fun myTimer(){
        object : CountDownTimer(90000,1000){
            override fun onTick(p0: Long) {
                binding.sayac.text = "${p0/1000}"
            }

            override fun onFinish() {
                binding.sayac.text = "0"
            }

        }.start()
    }
}