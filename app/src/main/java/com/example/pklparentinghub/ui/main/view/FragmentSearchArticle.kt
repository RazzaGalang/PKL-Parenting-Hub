package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.databinding.FragmentSearchArticleBinding

class FragmentSearchArticle : Fragment() {

    private var _binding: FragmentSearchArticleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSearch()
    }

    private fun setUpSearch() {
        binding.etSearchArticle.setOnEditorActionListener(OnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = textView.text.toString()
                // Lakukan pencarian data dengan menggunakan teks query
                // Misalnya, mengirim permintaan pencarian ke server atau melakukan pencarian lokal.
                // Setelah menerima hasil pencarian, Anda dapat menampilkan data tersebut ke pengguna.
                // jngn lupa untuk msukan ke hystory
                // item hasil pake yang profil, item history pake yg item history search
                // aku bikinin recyclerview buat hasilnya di layout, smngt yh
                return@OnEditorActionListener true
            }
            false
        })
    }
}