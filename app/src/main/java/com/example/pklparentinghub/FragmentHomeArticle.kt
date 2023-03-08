package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pklparentinghub.databinding.FragmentHomeArticleBinding
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeAdapter

class FragmentHomeArticle : Fragment() {

    private var _binding: FragmentHomeArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter : ArticleHomeAdapter = ArticleHomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.articleRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}