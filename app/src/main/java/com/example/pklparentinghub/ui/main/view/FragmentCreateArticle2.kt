package com.example.pklparentinghub.ui.main.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.databinding.FragmentCreateArticle2Binding
import com.example.pklparentinghub.ui.base.CreateArticleViewModelFactory
import com.example.pklparentinghub.ui.base.ImageUploadViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.CreateArticleViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ImageUploadViewModel
import com.example.pklparentinghub.utils.Status
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FragmentCreateArticle2 : Fragment() {

    private lateinit var viewModel : CreateArticleViewModel
    private lateinit var imageViewModel : ImageUploadViewModel
    private var _binding: FragmentCreateArticle2Binding? = null
    private val binding get() = _binding!!
    private var isNullArticle = false
    lateinit var multipartBody: MultipartBody.Part
    lateinit var filename: String
    var validArticle = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticle2Binding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArgument()
        btnContinue()
        checkArticle()
        onBackPressed()
        setupViewModel()
        setupObserve()
    }

    private fun initArgument() {
        val valuesThumbnail = arguments?.getString("valuesThumbnail")?.toUri()
        val file = File(valuesThumbnail?.let { getPathFromMediaStore(requireContext(), it) }.toString())
        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), file)
        multipartBody = MultipartBody.Part.createFormData("foto", file.name, requestFile)
    }

    private fun getPathFromMediaStore(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return path
    }

    private fun navigateAction(){
        val action = FragmentCreateArticle2Directions.actionToFragmentCreateArticle()
        findNavController().navigate(action)
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateAction()
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
    }

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this,
            CreateArticleViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[CreateArticleViewModel::class.java]

        imageViewModel = ViewModelProvider(
            this, ImageUploadViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ImageUploadViewModel::class.java]
    }

    private fun btnContinue() {
        binding.btnCreateArticle.setOnClickListener {
            if (isNullArticle() && validArticle){
                viewModel.requestArticle(
                    title = arguments?.getString("valuesTitle").toString(),
                    content = binding.etArticle.text.toString(),
                    thumbnail = filename)
            }
        }
    }

    private fun setupObserve() {
        imageViewModel.postImage(filename = multipartBody)
        imageViewModel.imageResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        filename = it.data.toString()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        viewModel.articleResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Berhasil woy", Toast.LENGTH_LONG).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    private fun checkArticle(){
        binding.etArticle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullArticle()
                }   else if((s?.length!! < 100)){
                    regexMinArticle()
                } else{
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