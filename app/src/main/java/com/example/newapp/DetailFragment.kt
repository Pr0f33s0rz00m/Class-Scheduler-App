package com.example.newapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.example.newapp.databinding.FragmentDetailBinding
import java.util.Locale
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.ParseException
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import java.util.Date


class DetailFragment : Fragment() {
    private var formattedDate: String = ""
    private var _binding: FragmentDetailBinding? = null
    private val viewModel: ItemViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.className.observe(viewLifecycleOwner, Observer { className ->

        // Update UI with new className
        })

        viewModel.details.observe(viewLifecycleOwner, Observer { details ->
            // Update UI with new details
        })

        viewModel.date.observe(viewLifecycleOwner, Observer { date ->
            // Update UI with new date
        })

        viewModel.ind.observe(viewLifecycleOwner, Observer { ind ->
            // Update UI with new ind
        })


        // Observer for the selected item index


        val itematindex = viewModel.getItemAtIndex(args.ind)
        if (itematindex != null) {
            viewModel.setCurrentItem(itematindex)
        }
        //if (itematindex != null) {
        //    binding.classdetails.setText(itematindex.details)
        //    binding.textInputEditTextNewClass.setText(itematindex.className)
        // }

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
                    binding.datePicker2.updateDate(year, month, day)

                }
             }

            // var languages =
            //    arrayOf("Homework","Test","Quiz","Project","Lab","Discussion","Speech","Paper")

            // var adapter: ArrayAdapter<CharSequence> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,languages)

            // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


            // Use 'formattedDate' when saving
            binding.buttonBackToList.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_listFragment)
            }
            // changing the textview
            // data to selected date


            binding.buttonEdit.setOnClickListener {
                binding.textInputClassDetails.visibility = View.VISIBLE
                binding.textInputClassName.visibility = View.VISIBLE
                binding.datePicker2.visibility = View.VISIBLE
                binding.buttonCancel.setOnClickListener {
                    binding.textInputClassDetails.visibility = View.INVISIBLE
                    binding.textInputClassName.visibility = View.INVISIBLE
                    binding.datePicker2.visibility = View.INVISIBLE
                }
                binding.textInputClassDetails.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val text = v.text.toString()
                        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                        return@OnEditorActionListener true
                    }
                    false
                })
                binding.textInputClassName.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val text = v.text.toString()
                        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                        return@OnEditorActionListener true
                    }
                    false
                })

                binding.datePicker2.setOnDateChangedListener() { view: DatePicker, year, month, dayOfMonth ->
                    val month = month + 1
                    formattedDate = "$year-$month-$dayOfMonth"
                    val s_year = year
                    val s_month = month

                    Log.d("DatePicker", "Date selected: $formattedDate")

                }
                binding.buttonSave.setOnClickListener {
                    val snackbar = Snackbar.make(it, "Do you want to save these changes?", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Yes") {
                        // Handle the "Yes" action, e.g., save the data
                        viewModel.updateItem(binding.textInputClassName.text.toString(),binding.textInputClassDetails.text.toString(),formattedDate,args.ind)
                        binding.textInputClassDetails.visibility = View.INVISIBLE
                        binding.textInputClassName.visibility = View.INVISIBLE
                        binding.datePicker2.visibility = View.INVISIBLE
                    }
                    snackbar.show()
                }


            }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

