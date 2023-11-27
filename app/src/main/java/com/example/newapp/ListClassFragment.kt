package com.example.newapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newapp.databinding.FragmentListBinding
import com.example.newapp.ui.login.database.UserDao


class ListClassFragment : Fragment() {

    private val viewModel: ItemViewModel by activityViewModels()

    private lateinit var listView: ListView
    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root


    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val navController = findNavController()
        viewModel.setClassList()
        val classList = viewModel.getAll()
        val classListIterator = classList.listIterator()
        val cl = mutableListOf<String>()
        while (classListIterator.hasNext()){
            cl.add(classListIterator.next().className)
        }
        val classAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_activated_1,cl)
        val listView = binding.listView
        listView.adapter = classAdapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // val selectedclass = parent.getItemAtPosition(position) as String
            // val itematindex = viewModel.getItemAtIndex(position)
            val selected = parent.getItemAtPosition(position) as String
            viewModel.index = position
            viewModel.setCurrentClass(selected)
            navController.navigate(R.id.action_listFragment_to_detailFragment)
        }

        binding.buttonAddClass.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_addFragment)
        }

    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }




}