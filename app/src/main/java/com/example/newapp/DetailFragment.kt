package com.example.newapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

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
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val itematindex1 = viewModel.getItemAtIndex(args.ind-1)
        if (itematindex1 != null) {
            binding.textView6.setText(itematindex1.date)
            binding.textView8.setText(itematindex1.className)
            binding.textViewDetail.setText(itematindex1.details)
        }
        binding.buttonCancelDetail.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)
        }
        binding.imageButtonHome.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_listFragment)

        }
        binding.imageButtonEdit.setOnClickListener {

            val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(args.ind-1)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}