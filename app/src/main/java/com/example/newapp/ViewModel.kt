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

    var itemsLiveData = mutableListOf<Item>()
    var classNames = arrayListOf<String>()
    private var _Item = MutableLiveData<Item?>(null)
    var index: Int = 0
    val Item: MutableLiveData<Item?>
        get() = _Item




    init {
        initClassData()
    }

    //public data class Item(
    //    val className: String,
    //    val details: String,
    //    val date: String,
    //    val ind: Int// Assuming date is a string, you might use LocalDate or Date depending on your requirements
    //)


    fun initClassData() {
        val classData = mutableListOf<Item>()
        deleteAll()
        // classData.add(Item(className = "CS 324", classDetails = "Algorithms and Analysis", date = "2023-10-04"))
        // loadClasses(classData)
        itemsLiveData = getAll()
        setClassList()
    }
    fun addClass(newClass: Item){
        runBlocking {
            viewModelScope.launch{
                dao.insert(newClass)
                itemsLiveData = dao.getAll().toMutableList()
            }
            _Item.value=newClass
        }
    }
    fun updateClass(updatedClass: Item){
        runBlocking {
            viewModelScope.launch{
                dao.update(updatedClass)
                itemsLiveData = dao.getAll().toMutableList()
            }

        }
        _Item.value = updatedClass
    }

    fun deleteAll(){
        runBlocking {
            viewModelScope.launch {
                dao.deleteAll()
            }
        }
    }
    fun loadClasses(initialClassData: MutableList<Item>) {
        runBlocking {
            launch {
                for (eachItem in initialClassData) {
                    dao.insert(eachItem)
                }
            }
        }
    }

    fun getAll(): MutableList<Item> {
        var classes = mutableListOf<Item>()
        runBlocking {
            launch {
                classes = dao.getAll().toMutableList()
            }
        }
        return classes
    }
    fun setClassList(){
        classNames.clear()
        for(item in itemsLiveData){
            classNames.add(item.className)
        }

    }
    fun setCurrentClass(className: String){
        runBlocking {
            viewModelScope.launch {
                val currentClass = dao.getByClassName(className)
                _Item.value = currentClass
            }
        }

    }
    fun getClass(index: Int){
        runBlocking {
            viewModelScope.launch {
                dao.get(index)
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


