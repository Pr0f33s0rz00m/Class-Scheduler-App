package com.example.newapp
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.*
import com.example.newapp.LandingScreenActivity
import com.example.newapp.databinding.ActivityLandingScreenBinding

class ItemViewModel : ViewModel() {
    private val _itemsLiveData = MutableLiveData<MutableList<Item>>()
    private val _currentItem = MediatorLiveData<Item>() // Current item LiveData
    private val _className = MutableLiveData<String>()

    val className: LiveData<String> = _className

    private val _details = MutableLiveData<String>()
    val details: LiveData<String> = _details

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _ind = MutableLiveData<Int>()
    val ind: LiveData<Int> = _ind

    val currentItem: LiveData<Item>
        get() = _currentItem
    val itemsLiveData: LiveData<MutableList<Item>>
        get() = _itemsLiveData
    // LiveData for the selected item index
    private val _selectedItemIndex = MutableLiveData<Int>()

    private var index = 0
    // LiveData to hold the details of the selected item
    fun setCurrentItem(item: Item) {
        _className.value = item.className
        _details.value = item.details
        _date.value = item.date
        _ind.value = item.ind
    }

    // Function to update properties of the item
    fun updateItem(className: String, details: String, date: String, ind: Int) {
        _className.value = className
        _details.value = details
        _date.value = date
        _ind.value = ind
        _itemsLiveData.value?.let {
            if (ind in it.indices) {
                it[ind] = Item(className, details, date, ind)
                _itemsLiveData.value = it // Trigger LiveData to update observers.
            }
        }

        // Additional logic to handle the updated item
    }

    init {
        // Initialize with an empty list
        _itemsLiveData.value = mutableListOf()
    }

    fun getNumberOfItems(): Int {
        return _itemsLiveData.value?.size ?: 0
    }
    fun getList(): List<Item> {

        return _itemsLiveData.value.orEmpty()
    }
    fun getItemAtIndex(index: Int): Item? {
        // Return the item at the specified index if it exists, otherwise return null
        return _itemsLiveData.value?.getOrNull(index)
    }
    // Function to set the selected item
    fun selectItem(index: Int) {
        if (index in _itemsLiveData.value?.indices ?: 0..-1) {
            _selectedItemIndex.value = index

        }
    }
    public data class Item(
        val className: String,
        val details: String,
        val date: String,
        val ind: Int// Assuming date is a string, you might use LocalDate or Date depending on your requirements
    )
    init {
        _itemsLiveData.value = mutableListOf()

        // Update current item whenever the selected index changes
        _currentItem.addSource(_selectedItemIndex) { selectedIndex ->
            _currentItem.value = _itemsLiveData.value?.getOrNull(selectedIndex)
        }
    }
    // public data class Event(
    //    val eventNAME: String,
    //    val eventTYPE: String,
    //    val eventDATE: String,
    //    val eventINDEX: Int// Assuming date is a string, you might use LocalDate or Date depending on your requirements
    //)
    // Function to add an item to the list
    fun addItem(className: String, details: String, date: String,ind: Int) {

        val newItem = Item(className, details, date, index)
        val updatedList = _itemsLiveData.value.orEmpty().toMutableList() // Create a new list
        index++
        updatedList.add(newItem)
        _itemsLiveData.value = updatedList // Update LiveData with the new list

    }



// Calling this function refreshes the data within the ViewModel


    // Function to alter/edit an item in the list
    //fun editItem(position: Int, className: String, details: String, date: String) {
   //     _itemsLiveData.value?.let {
   //         if (position in it.indices) {
   //             it[position] = Item(className, details, date, position)
   //             _itemsLiveData.value = it // Trigger LiveData to update observers.
   //         }
   //     }
   // }

    fun deleteItem(position: Int) {
        _itemsLiveData.value?.let {
            if (position in it.indices) {
                it.removeAt(position)
                _itemsLiveData.value = it // Trigger LiveData to update observers.
            }
        }
    }
}
    // Optionally, if you want to observe the items list, you can expose it as LiveData
    // This is beneficial if you want UI components to observe changes in the list
