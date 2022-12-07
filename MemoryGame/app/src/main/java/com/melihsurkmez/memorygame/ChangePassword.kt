package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.melihsurkmez.memorygame.databinding.ActivityChangePasswordBinding
import okhttp3.*
import java.io.IOException

class ChangePassword : AppCompatActivity() {
    lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sifreButonu.setOnClickListener {

            val username=binding.sifreKullaniciadi.text.toString()
            val old_password=binding.eskiSifre.text.toString()
            val new_password=binding.yeniSifre.text.toString()
            val new_password2=binding.yeniSifre2.text.toString()

            if(new_password != new_password2){

                binding.yeniSifre.error = "Girilen yeni şifreler birbiriyle eşleşmiyor."

            }
            else{

                change(username,old_password,new_password)

            }



        }



    }

    fun change(username: String,old_password: String, new_password: String){

        val parameter = username+"-"+old_password+"-"+new_password
        val URL:String = " http://192.168.1.101:5000/api/chgpassword/"+parameter

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
                                    Toast.makeText(this@ChangePassword, "Şifre başarıyla güncellendi!", Toast.LENGTH_LONG).show()
                                }

                            }
                            else{

                                Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(this@ChangePassword, "Kullanıcı adını veya şifreyi kontrol edin!", Toast.LENGTH_LONG).show()
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