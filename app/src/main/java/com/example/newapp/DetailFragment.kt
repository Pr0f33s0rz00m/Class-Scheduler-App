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
import com.example.newapp.ui.login.database.Item
import com.google.android.material.snackbar.Snackbar
import java.util.Date


class DetailFragment : Fragment() {
    private var formattedDate: String = ""
    private var _binding: FragmentDetailBinding? = null
    private val viewModel: ItemViewModel by activityViewModels()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding layout and inflating
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        // Binding the viewModel and the Life cycle owner
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.item.observe(viewLifecycleOwner, Observer {

            // Update UI with new info
        })


        // Observer for the selected item index



        //if (itematindex != null) {
        //    binding.classdetails.setText(itematindex.details)
        //    binding.textInputEditTextNewClass.setText(itematindex.className)
        // }

        val currentclass = viewModel.item.value
        currentclass?.let {
            // if there is a class selected then its date is pulled from the particular class
            if (it != null) {
                formattedDate = it.date
            }

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

           // Sets the back button to navigate back to the list
            binding.buttonBackToList.setOnClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_listFragment)
            }
            // changing the textview
            // data to selected date

            // Binds the button edit
            binding.buttonEdit.setOnClickListener {
                // Once the button is clicked, the edit windows become visible
                binding.textInputClassDetails.visibility = View.VISIBLE
                binding.textInputClassName.visibility = View.VISIBLE
                binding.datePicker2.visibility = View.VISIBLE
                binding.buttonCancel.setOnClickListener {
                    binding.textInputClassDetails.visibility = View.INVISIBLE
                    binding.textInputClassName.visibility = View.INVISIBLE
                    binding.datePicker2.visibility = View.INVISIBLE
                }
                // Binds edit text windows
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
                // Binds date picker and saves the changed date
                binding.datePicker2.setOnDateChangedListener() { view: DatePicker, year, month, dayOfMonth ->
                    val month = month + 1
                    formattedDate = "$year-$month-$dayOfMonth"
                    val s_year = year
                    val s_month = month

                    Log.d("DatePicker", "Date selected: $formattedDate")

                }
                // Once the class is saved, it sends the updates to the database and moves to the list fragment
                binding.buttonSave.setOnClickListener {
                    val snackbar = Snackbar.make(it, "Do you want to save these changes?", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Yes") {

                        // Handle the "Yes" action, e.g., save the data
                        val item: Item? = currentclass?.let { it1 -> Item(id = it1.id,  className =  binding.textInputClassName.text.toString(), classDetails = binding.textInputClassDetails.text.toString(),date = formattedDate) }
                        if (item != null) {
                            viewModel.updateClass(item)

                        }
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

