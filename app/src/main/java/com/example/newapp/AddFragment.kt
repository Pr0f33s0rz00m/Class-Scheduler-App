package com.example.newapp

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newapp.databinding.FragmentAddBinding
import java.util.Date
import java.util.Locale
import androidx.lifecycle.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val viewModel: ItemViewModel by activityViewModels()
    private var indexadd: Int = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBinding.inflate(inflater, container, false)

       val view = binding.root
       return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var date : String = ""
        date = SimpleDateFormat("yyyy-MM-dd").format(Date()).toString()
        binding.datePicker1.setOnDateChangedListener(){ view: DatePicker, year, month, dayOfMonth ->
            val month = month + 1
            val s_year = year-1
            date = "$s_year-$month-$dayOfMonth"
            Log.d("DatePicker", "Date selected: $date")

        }
        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        binding.imageButtonSave.setOnClickListener {
            val details = binding.edittextclassdetails.text.toString()
            val name = binding.textInputClassName.text.toString()
            viewModel.addItem(name, details, date.toString(), indexadd)
            ++indexadd
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        binding.imageButtonHome.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}