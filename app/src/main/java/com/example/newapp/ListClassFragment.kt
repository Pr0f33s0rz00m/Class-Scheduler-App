package com.example.newapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newapp.databinding.FragmentListBinding


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
        // Binds and inflates the view
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root


    return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Defines and sets the nav controller
        val navController = findNavController()
        viewModel.setClassList()
        // Defines and sets a layout manager for the recycler view
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        layoutManager.reverseLayout = true
        binding.recyclerView.layoutManager = layoutManager

        val classList = viewModel.getAll().map { it.className }
        val classPosition = viewModel.getAll().map {it.id}

        // Defines the recycler adapter using the adapter class and an onclicker for class positions
        val classAdapter = RecyclerAdapter(classList, object : RecyclerAdapter.OnClickListener {
            override fun onClick(position: Int) {
                // Handle the item click event

                val selectedItem = classList[position]

                viewModel.setCurrentClass(selectedItem)

                viewModel.getClass(classPosition[position])
                Log.d("position", classPosition[position].toString())
                findNavController().navigate(R.id.action_listFragment_to_detailFragment)
                // Perform actions with the selected item, like showing details
            }
        })

        // Sets the adapter to the newly created adapter
        binding.recyclerView.adapter = classAdapter


        // observes items live data for changes
        viewModel.itemsLiveData.observe(viewLifecycleOwner, { items ->
            //Assuming you have a method in your adapter to update the dataset
            classAdapter.submitList(items.map { it.className })

        })

        // Sets the add button to add a new class and navigate to the add fragment
        binding.buttonAddClass.setOnClickListener {
            navController.navigate(R.id.action_listFragment_to_addFragment)
        }

    }

            override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }




}