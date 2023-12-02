package com.example.newapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newapp.R
import kotlin.random.Random


class RecyclerAdapter(private var classList: List<String>, public val listener: OnClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val textViewClassName: TextView = itemView.findViewById(R.id.tvClassName)

        init {
            // sets clicker for each class position
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onClick(position)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_class, parent, false)
        return ViewHolder(itemView)
    }
    // gets class amount

    override fun getItemCount() = classList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentClass = classList[position]
        // For even positions, the color is blue
        if(((position+1) % 2) == 0 ){
            holder.textViewClassName.setBackgroundColor(Color.BLUE)
        }
        // Odd position are cyan
        else{
            holder.textViewClassName.setBackgroundColor(Color.CYAN)
        }

        // sets class to current class
        holder.textViewClassName.text = currentClass
    }

    fun submitList(newList: List<String>) {
        classList = newList
        notifyDataSetChanged()
    }
}
