package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.melihsurkmez.memorygame.databinding.ActivityPreferencesBinding
import kotlinx.android.synthetic.main.activity_preferences.*

class Preferences : AppCompatActivity() {
    var zorlukderece: String?=""
    var kisisayisi: String=""
    lateinit var radioButton: RadioButton
    lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        oyuncusayisi.setOnCheckedChangeListener { group,checkedId ->
            if(checkedId == R.id.tekkisilik){
                kisisayisi="Tek"
                println("a")
            }
            if(checkedId== R.id.ciftkisilik){
                kisisayisi="Çift"
                println("a")
            }
        }



        zorlukderecesigroup.setOnCheckedChangeListener { group,checkedId ->
            if(checkedId == binding.kolay.id){
                zorlukderece  = "Kolay"
                println("a")
            }
            if(checkedId == R.id.orta){
                zorlukderece  = "Orta"
                println("a")
            }
            if(checkedId == R.id.zor){
                zorlukderece  = "Zor"
                println("a")
            }
        }

        binding.oyunbutonu.setOnClickListener {

            println(kisisayisi+zorlukderece)

            if(kisisayisi!="" && zorlukderece!=""){

                intent = Intent(applicationContext,SplashScreen::class.java)
                intent.putExtra("zorlukderece", zorlukderece);
                intent.putExtra("kisisayisi", kisisayisi);
                startActivity(intent)


            }

            else{

                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(this@Preferences,"Tercihleri kontrol ediniz!", Toast.LENGTH_LONG).show()
                }
            }





        }







    }
}