package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.melihsurkmez.memorygame.databinding.ActivityMainBinding
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Butona tıklandığında gerçekleşen işlemler

        binding.girisButonu.setOnClickListener {

            var giris_kullaniciadi=binding.kullaniciadi.text.toString()
            var giris_sifre=binding.sifre.text.toString()

            if(giris_kullaniciadi.contains('-')){

                binding.kullaniciadi.error="Lütfen kullanıcı adında '-' özel karakterler kullanamayınız!"
                return@setOnClickListener
            }

            if(giris_sifre.contains('-')){

                binding.sifre.error = "Lütfen şifrenizde '-' şeklinde özel karakter barındırmayınız!"
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(giris_kullaniciadi)){

                binding.kullaniciadi.error = "Lütfen kullanıcı adınızı giriniz!"

                return@setOnClickListener
            }

            else if(TextUtils.isEmpty(giris_sifre)){

                binding.sifre.error = "Lütfen şifrenizi giriniz."

                return@setOnClickListener
            }
            login_control(giris_kullaniciadi,giris_sifre)

        }

        binding.kaydol.setOnClickListener {
            intent = Intent(applicationContext,Register::class.java)
            startActivity(intent)
        }
        binding.sifreUnut.setOnClickListener {
            intent= Intent(applicationContext,ChangePassword::class.java)
            startActivity(intent)
        }



    }

    fun login_control(username:String,password:String){

        val parameter = username+"-"+password
        val URL:String = " http://192.168.1.101:5000/api/login/"+parameter

        if(URL.isNotEmpty()){

            val client = OkHttpClient()

            val request = Request.Builder()
                .url(URL)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Debug")

                    response.use {

                        if(!response.isSuccessful){

                            println("Error")

                        }
                        else{

                            val body = response?.body?.string()

                            val response: String?=body.toString()
                            println(response)
                            if(response=="OK"){

                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(this@MainActivity, "Giriş Başarılı!", Toast.LENGTH_LONG).show()
                                }

                            }
                            else{

                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(this@MainActivity, "Kullanıcı adı veya şifre hatalı!", Toast.LENGTH_LONG).show()
                                }

                            }


                        }
                    }
                }
            })

        }
        else{

            println("Tehlike")

        }







    }


}