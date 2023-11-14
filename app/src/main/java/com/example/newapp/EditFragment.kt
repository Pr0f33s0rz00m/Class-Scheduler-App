package com.example.newapp

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.ParseException
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newapp.databinding.FragmentEditBinding
import java.time.LocalDate
import java.util.Date
import java.util.Locale


class EditFragment : Fragment() {
    private var formattedDate: String? = null
    private var _binding: FragmentEditBinding? = null
    private val viewModel: ItemViewModel by activityViewModels()
    private val args: EditFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var itematindex = viewModel.getItemAtIndex(args.index)
        if (itematindex != null) {
            binding.classdetails.setText(itematindex.details)
            binding.textInputEditTextNewClass.setText(itematindex.className)
        }

        itematindex?.let {
            formattedDate = it.date
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: Date? = try {
                format.parse(it.date)
            } catch (e: ParseException) {
                null // Handle the case where parsing fails
            }


        date?.let {
          val calendar = Calendar.getInstance()
                calendar.time = it
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                binding.datePicker1.updateDate(year, month, day)
            }
        }

        // var languages =
        //    arrayOf("Homework","Test","Quiz","Project","Lab","Discussion","Speech","Paper")

        // var adapter: ArrayAdapter<CharSequence> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,languages)

        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.textInputEditTextNewClass.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = v.text.toString()
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                return@OnEditorActionListener true
            }
            false
        })
        binding.classdetails.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = v.text.toString()
                Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                return@OnEditorActionListener true
            }
            false
        })

        binding.datePicker1.setOnDateChangedListener { _, year, month, dayOfMonth ->
            // Adjust the month value and format the date
            val adjustedMonth = month + 1
            formattedDate = "$year-${adjustedMonth.toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}"
        }

        // Use 'formattedDate' when saving
        binding.imageButtonSave.setOnClickListener {
            viewModel.editItem(args.index+1,
                binding.textInputEditTextNewClass.text.toString(),
                binding.classdetails.text.toString(),
                formattedDate ?: "")
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }
            // changing the textview
            // data to selected date


        binding.buttonEditCancel.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }
        binding.imageButtonHome.setOnClickListener {
            findNavController().navigate(R.id.action_editFragment_to_listFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}