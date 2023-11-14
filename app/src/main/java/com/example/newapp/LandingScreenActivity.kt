package com.example.newapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.newapp.databinding.ActivityLandingScreenBinding


class LandingScreenActivity : AppCompatActivity() {
    private lateinit var viewModel: ItemViewModel

    private lateinit var binding: ActivityLandingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findNavController(R.id.nav_host_fragment).navigate(R.id.action_listFragment_self)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        // val navController = findNavController(R.id.navgraph)
        // var first = ListFragment()
        // supportFragmentManager.beginTransaction()
        //    .add(R.id.nav_host_fragment, first).commit()


    }
}