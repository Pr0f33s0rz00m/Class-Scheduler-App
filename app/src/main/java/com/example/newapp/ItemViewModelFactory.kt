package com.example.newapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newapp.ui.login.database.*
import com.example.newapp.ItemViewModel
class ItemViewModelFactory(private val dao: UserDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
        return ItemViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}