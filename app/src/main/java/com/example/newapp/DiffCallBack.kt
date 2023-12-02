package com.example.newapp

import androidx.recyclerview.widget.DiffUtil

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        // Return true if the items represent the same object or have the same data identifier
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        // Return true if the visual representation of the items is the same
        // This method is only called if areItemsTheSame returned true
        return oldItem == newItem
    }
}