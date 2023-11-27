package com.example.newapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.newapp.databinding.ActivityLandingScreenBinding
import com.example.newapp.ui.login.database.ItemDatabase


class LandingScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: ItemViewModel

    private lateinit var binding: ActivityLandingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val don = ItemDatabase.getInstance(application).itemDAO
        val itemViewModelFactory = ItemViewModelFactory(don)
        val itemViewModel = ViewModelProvider(this,itemViewModelFactory).get(ItemViewModel::class.java)

        binding = ActivityLandingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)



        navController.navigate(R.id.listFragment)
        // val navController = findNavController(R.id.navgraph)
        // var first = ListFragment()
        // supportFragmentManager.beginTransaction()
        //    .add(R.id.nav_host_fragment, first).commit()


    }
}