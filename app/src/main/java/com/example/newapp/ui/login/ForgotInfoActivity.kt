package com.example.newapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// import com.example.newapp.databinding.ActivityLogin2Binding
import com.example.newapp.databinding.ForgotUsernamePasswordBinding
class ForgotInfoActivity : AppCompatActivity() {
        private lateinit var binding: ForgotUsernamePasswordBinding
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ForgotUsernamePasswordBinding.inflate(layoutInflater)
            setContentView(binding.root)
            var email = binding.emailPasswd
            var username = binding.emailUsername
            binding.textView4.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

}