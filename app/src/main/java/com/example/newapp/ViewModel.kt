package com.example.newapp
import android.media.CamcorderProfile.getAll
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch

import androidx.lifecycle.*
import com.example.newapp.ui.login.database.Item
import com.example.newapp.ui.login.database.UserDao
import kotlinx.coroutines.runBlocking

class ItemViewModel(var dao: UserDao) : ViewModel() {

    private val _itemsLiveData = MutableLiveData<List<Item>>()
    val itemsLiveData: LiveData<List<Item>> = _itemsLiveData
    private var classNames = mutableListOf<String>()
    // LiveData for a single item
    private var _Item = MutableLiveData<Item?>()
    val item: LiveData<Item?> = _Item


    init {
        initClassData()
    }

    //public data class Item(
    //    val className: String,
    //    val details: String,
    //    val date: String,
    //    val ind: Int// Assuming date is a string, you might use LocalDate or Date depending on your requirements
    //)

    // Initializes the viewmodel
    private fun initClassData() {
        val classData = mutableListOf<Item>()
        deleteAll()
        runBlocking {
            viewModelScope.launch {

                _itemsLiveData.value = dao.getAll()
                setClassList()
            }
        }

    }
    // Based on the item that's passed, a new class is created and added to the database
    fun addClass(newClass: Item) {
        runBlocking {
            viewModelScope.launch {
                viewModelScope.launch {
                    dao.insert(newClass)
                    refreshItemsList()
                }
            }

        }

    }
    // Refreshes the live data based on the classes in the database
    private fun refreshItemsList() {
        viewModelScope.launch {
            _itemsLiveData.value = dao.getAll()
        }
    }
    // Based on the index of an item, a particular class is updated
    fun updateClass(updatedClass: Item){
        runBlocking {
            viewModelScope.launch{
                dao.update(updatedClass)
                refreshItemsList()
            }

        }
        _Item.value = updatedClass
    }
    // Deletes all classes from database
    fun deleteAll(){
        runBlocking {
            viewModelScope.launch {
                dao.deleteAll()
            }
        }
    }
    // Loads classes from live data into the database
    fun loadClasses(initialClassData: MutableList<Item>) {
        runBlocking {
            launch {
                for (eachItem in initialClassData) {
                    dao.insert(eachItem)
                }
            }
        }
    }
    // gets a listing of all current classes in the database
    fun getAll(): MutableList<Item> {
        var classes = mutableListOf<Item>()
        runBlocking {
            launch {
                classes = dao.getAll().toMutableList()
            }
        }
        return classes
    }
    // Sets the class list to the classname array based on class names in the database
    fun setClassList(){
        classNames.clear()
        _itemsLiveData.value?.let { items ->
            for (item in items) {
                classNames.add(item.className)
            }
        }
    }
    // Sets current class based on its class name and assigns it to the Item value
    fun setCurrentClass(className: String){
        runBlocking {
            viewModelScope.launch {
                val currentClass = dao.getByClassName(className)
                _Item.value = currentClass

            }
        }

    }
    // Gets a class based on its index
    fun getClass(index: Int){
        runBlocking {
            viewModelScope.launch {
                _Item.value = dao.get(index)
            }
        }

    }


    // public data class Event(
    //    val eventNAME: String,
    //    val eventTYPE: String,
    //    val eventDATE: String,
    //    val eventINDEX: Int// Assuming date is a string, you might use LocalDate or Date depending on your requirements
    //)
    // Function to add an item to the list
}



// Calling this function refreshes the data within the ViewModel


    // Function to alter/edit an item in the list


// Optionally, if you want to observe the items list, you can expose it as LiveData
// This is beneficial if you want UI components to observe changes in the list


