package com.melihsurkmez.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.melihsurkmez.memorygame.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Database conf yapılan yer
        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("Users")


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




            else{

                auth.createUserWithEmailAndPassword(binding.kayitEmail.text.toString(),binding.kayitSifre.text.toString())
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            // Kullanıcı bilgilerini alıyorum.
                            var currentUser = auth.currentUser
                            // İlgili detaylar realtime'e kaydediyorum.
                            var currentUserDatabase=currentUser?.let { it1 -> databaseReference?.child(it1.uid) }
                            currentUserDatabase?.child("kullaniciadi")?.setValue(binding.kayitKullaniciadi.text.toString())
                            currentUserDatabase?.child("email")?.setValue(binding.kayitEmail.text.toString())
                            currentUserDatabase?.child("sifre")?.setValue(binding.kayitSifre.text.toString())
                            Toast.makeText(this@Register,"Kayıt başarıyla gerçekleşti.",Toast.LENGTH_LONG).show()

                        }
                        else{
                            Toast.makeText(this@Register,"Kayıt gerçekleştirilemedi.",Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }



    }
}