package com.melihsurkmez.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.ImageButton
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.melihsurkmez.memorygame.databinding.ActivityGameBinding
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Game : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding
    //Butonlarimizin listesi burada olucak
    private lateinit var buttons: List<ImageButton>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        get_data()


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

    fun get_data() {

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.1.101:5001/")
            .build()
            .create(ApiInterface::class.java)


        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<CardsItem>?> {
            override fun onResponse(
                call: Call<List<CardsItem>?>,
                response: retrofit2.Response<List<CardsItem>?>
            ) {
                val responseBody = response.body()!!
                val myStringBuilder = StringBuilder()
                for(Cards in responseBody){
                    myStringBuilder.append(Cards.cardId)
                    myStringBuilder.append("\n")
                }

                println(myStringBuilder)


            }

            override fun onFailure(call: Call<List<CardsItem>?>, t: Throwable) {
                println(t.message)
            }
        })




    }
}