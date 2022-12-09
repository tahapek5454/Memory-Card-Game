package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.melihsurkmez.memorygame.databinding.ActivityRegisterBinding
import okhttp3.*
import java.io.IOException

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Database conf yapılan yer



        binding.kayitButonu.setOnClickListener {

            var username=binding.kayitKullaniciadi.text.toString()
            var email=binding.kayitEmail.text.toString()
            var password=binding.kayitSifre.text.toString()
            var re_password=binding.kayitSifre2.text.toString()

            if(TextUtils.isEmpty(username)){

                binding.kayitKullaniciadi.error = "Lütfen kullanıcı adınızı giriniz!"

                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(email)){
                binding.kayitEmail.error = "Lütfen email adresinizi giriniz!"

                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(password)){
                binding.kayitSifre.error = "Lütfen parolanızı giriniz!"

                return@setOnClickListener
            }
            else if(TextUtils.isEmpty(re_password)){
                binding.kayitSifre2.error = "Lütfen tekrar parolanızı giriniz!"

                return@setOnClickListener
            }

            if(password.length<=6){
                binding.kayitSifre.error="Şifre uzunluğu 6 karakterden fazla olmalıdır!"

                return@setOnClickListener

            }


            if (password != re_password){

                binding.kayitSifre2.error = "Parolalar eşleşmiyor!"

                return@setOnClickListener
            }

            register(username,email,password)



            }
        }

    fun register(username: String, email: String, password: String){

        val parameter = username+"-"+email+"-"+password
        val URL:String = " http://192.168.1.101:5000/api/register/"+parameter

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
                                    Toast.makeText(this@Register, "Kayıt işlemi başarılı bir şekilde gerçekleşti.", Toast.LENGTH_LONG).show()
                                }

                            }
                            else{

                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(this@Register, "Bu kullanıcı zaten kayıtlı.", Toast.LENGTH_LONG).show()
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
