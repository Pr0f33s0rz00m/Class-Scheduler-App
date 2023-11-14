package com.example.newapp
import androidx.activity.viewModels
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class ItemViewModel : ViewModel() {
    private val _itemsLiveData = MutableLiveData<MutableList<Item>>()
    val itemsLiveData: LiveData<MutableList<Item>>
        get() = _itemsLiveData
    // LiveData for the selected item index
    private val _selectedItemIndex = MutableLiveData<Int>()
    private var index = 1
    // LiveData to hold the details of the selected item


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
    fun editItem(position: Int, className: String, details: String, date: String) {
        _itemsLiveData.value?.let {
            if (position in it.indices) {
                it[position] = Item(className, details, date, position)
                _itemsLiveData.value = it // Trigger LiveData to update observers.
            }
        }
    }

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
