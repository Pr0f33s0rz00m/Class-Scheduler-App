package com.example.newapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newapp.databinding.FragmentListBinding

private fun <T> ArrayAdapter<T>.addAll(stringList: Any) {

}

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
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
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val currentList = viewModel.getList()

        val classListIterator = currentList.listIterator()
        val classList = mutableListOf<String>()
        while(classListIterator.hasNext()){
            classList.add(classListIterator.next().className)
        }

        // var str = arrayOf<String>()


        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, classList)
        binding.listView.adapter = adapter


        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // val selectedclass = parent.getItemAtPosition(position) as String
            // val itematindex = viewModel.getItemAtIndex(position)

            val action = ListClassFragmentDirections.actionListFragmentToDetailFragment(position)
            findNavController().navigate(action)
            }



            binding.buttonAddClass.setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }

        }


        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }




}