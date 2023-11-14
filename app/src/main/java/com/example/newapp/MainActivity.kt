// This file contains the main activity which is the splash screen
// Once the splash screen is done, the login page will run
package com.example.newapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.newapp.ui.login.LoginActivity
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // loads splash screen via the activity_main.xml
        setContentView(R.layout.activity_main)

        // Ensures app opens in fullscreen mode
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // These similar code snippets perform animation on the splash screen elements
        val backgroundText: TextView = findViewById(R.id.logoText)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundText.startAnimation(slideAnimation)

        val backgroundText2: TextView = findViewById(R.id.classSchedulerText)
        val slideAnimation2 = AnimationUtils.loadAnimation(this, R.anim.side_slide)
        backgroundText2.startAnimation(slideAnimation2)

        val icon1: ImageView = findViewById(R.id.icon1)
        val icon1_animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        icon1.startAnimation(icon1_animation)

        val icon2: ImageView = findViewById(R.id.icon2)
        val icon2_animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        icon2.startAnimation(icon2_animation)

        val icon3: ImageView = findViewById(R.id.icon3)
        val icon3_animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        icon3.startAnimation(icon3_animation)

        val icon4: ImageView = findViewById(R.id.icon4)
        val icon4_animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        icon4.startAnimation(icon4_animation)

        // Post delayed ensures that splash screen only runs for 5 seconds
        // Once the splash screen has ran for 5 seconds, the login window will come up
        Handler().postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the splash screen activity
        }, 5000)
    }
}