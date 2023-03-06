package com.example.pklparentinghub

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.FragmentCreateArticle2Binding

class FragmentCreateArticle2 : Fragment() {

    private var _binding: FragmentCreateArticle2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticle2Binding.inflate(inflater, container, false)

        return binding.root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}