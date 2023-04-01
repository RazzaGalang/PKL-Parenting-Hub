package com.example.pklparentinghub.ui.main.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.articleData.ArticleRequest
import com.example.pklparentinghub.databinding.FragmentCreateArticle2Binding
import com.example.pklparentinghub.ui.base.CreateArticleViewModelFactory
import com.example.pklparentinghub.ui.base.ImageUploadViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.CreateArticleViewModel
import com.example.pklparentinghub.ui.main.viewmodel.ImageUploadViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FragmentCreateArticle2 : Fragment() {

    private lateinit var viewModel : CreateArticleViewModel
    private lateinit var imageViewModel : ImageUploadViewModel

    private var _binding: FragmentCreateArticle2Binding? = null
    private val binding get() = _binding!!

    lateinit var filename: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticle2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPath()
        setupViewModel()
        btnContinue()
        onBackPressed()
        onBackClick()
    }

    private fun setUpPath() {
        val valuesThumbnail = arguments?.getString("valuesThumbnail")?.toUri()
        val fileThumbnail = File(valuesThumbnail?.let { getPathFromMediaStore(requireContext(), it) }.toString())
        val requestFile = RequestBody.create("image/png".toMediaTypeOrNull(), fileThumbnail)
        val multipartBody = MultipartBody.Part.createFormData("image", fileThumbnail.name, requestFile)

        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    imageViewModel.postImage(token, multipartBody)
                }
        }
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

    private fun setupViewModel (){
        viewModel = ViewModelProvider(
            this, CreateArticleViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[CreateArticleViewModel::class.java]

        imageViewModel = ViewModelProvider(
            this, ImageUploadViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ImageUploadViewModel::class.java]
    }

    private fun setupObserveGetFileName() {
        imageViewModel.imageResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.let { item ->
                            filename = item.data?.body()?.data?.filename!!
                        }
                        Toast.makeText(requireContext(), "Bismillah success", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun btnContinue() {
        binding.btnCreateArticle.setOnClickListener {
            val valuesTitle = arguments?.getString("valuesTitle").toString()
            val valuesContent = binding.etArticle.text.toString()
            if (valuesContent.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.error_text_null_article), Toast.LENGTH_SHORT).show()
            } else if((valuesContent.length < 400)){
                Toast.makeText(requireContext(), getString(R.string.error_text_min_article), Toast.LENGTH_SHORT).show()
            } else{
                setupObserveGetFileName()
                lifecycleScope.launchWhenResumed {
                    AccessManager(requireContext())
                        .access
                        .collect { token ->
                            viewModel.requestArticle(token, ArticleRequest(
                                title = valuesTitle,
                                content = valuesContent,
                                thumbnail = filename
                            ))
                        }
                }
                setupObservePostArticle()
            }
        }
    }

    private fun setupObservePostArticle() {
        viewModel.articleResult.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        Toast.makeText(requireContext(), "Aamiin", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun onBackClick(){
        binding.topAppBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}