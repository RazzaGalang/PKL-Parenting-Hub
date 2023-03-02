package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pklparentinghub.databinding.FragmentHomeFavoriteArticleBinding
import com.example.pklparentinghub.ui.main.adapter.ArticleHomeFavoriteAdapter

class FragmentHomeFavoriteArticle : Fragment() {

    private var _binding: FragmentHomeFavoriteArticleBinding? = null
    private val binding get() = _binding!!
    private val adapter : ArticleHomeFavoriteAdapter = ArticleHomeFavoriteAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeFavoriteArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}