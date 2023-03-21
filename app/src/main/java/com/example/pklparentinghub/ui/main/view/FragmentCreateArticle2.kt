package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentCreateArticle2Binding

class FragmentCreateArticle2 : Fragment() {

    private var _binding: FragmentCreateArticle2Binding? = null
    private val binding get() = _binding!!
    private var isNullArticle = false
    private val minArticleRegex = ".{100,}"
    var validArticle = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticle2Binding.inflate(inflater, container, false)

        checkArticle()
        btnCreateOnClick()

        return binding.root
    }

    private fun btnCreateOnClick() {
        binding.btnCreateArticle.setOnClickListener{
            validationMinArticle()
            validationTrue()
        }
    }

    private fun validationTrue(){
        if(isNullArticle() && validArticle) binding()
    }

    private fun validationMinArticle(){
        if(binding.etArticle.toString().matches(minArticleRegex.toRegex())) {
            regexMinArticle()
        } else clearArticle()
    }

    private fun binding(){
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkArticle(){
        binding.etArticle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullArticle()
                }   else {
                    clearArticle()
                }
            }
        })
    }

    private fun isNullArticle(): Boolean{
        isNullArticle = if (binding.etArticle.length() == 0){
            nullArticle()
            false
        } else {
            true
        }
        return isNullArticle
    }

    private fun nullArticle(): Boolean{
        binding.tilArticle.error = getString(R.string.null_article)
        binding.etArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
        return false
    }

    private fun regexMinArticle(){
        binding.tilArticle.error = getString(R.string.min_article)
        binding.etArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
        validArticle = false
    }

    private fun clearArticle(){
        binding.tilArticle.isErrorEnabled = false
        binding.etArticle.setBackgroundResource(R.drawable.slr_outline_button_border)
        validArticle = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}