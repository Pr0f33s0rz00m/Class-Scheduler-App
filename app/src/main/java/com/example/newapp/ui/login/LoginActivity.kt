package com.example.newapp.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.newapp.LandingScreenActivity
import com.example.newapp.MainActivity
import com.example.newapp.databinding.ActivityLoginBinding
import com.example.newapp.R
import java.sql.Types.NULL

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forgotPassword.setOnClickListener {
           val intent = Intent(this, ForgotInfoActivity::class.java)
           startActivity(intent)
        }

        binding.password.setOnKeyListener(View.OnKeyListener{v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                updateUiWithUser()
                return@OnKeyListener true
            }
            false
        })

        binding.buttonLogin.setOnClickListener {

              updateUiWithUser()

            }



        val username = binding.username
        val password = binding.password
        val login = binding.buttonLogin
        val loading = binding.loading


    }

    private fun updateUiWithUser() {
        val welcome = getString(R.string.welcome)
        val displayName = getString(R.string.app_name)
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
        val intent = Intent(this,LandingScreenActivity::class.java)
        startActivity(intent)
    }


}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
