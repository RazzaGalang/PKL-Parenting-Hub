package com.example.pklparentinghub.ui.main.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentCreateArticleBinding
import com.example.pklparentinghub.ui.main.condition.CancelCreateArticle
import java.io.IOException
import java.io.InputStream

class FragmentCreateArticle : Fragment() {

    private var _binding: FragmentCreateArticleBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_IMAGE = 102

    private var selectedImageUri: Uri? = null

    private var isNullTitle = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivUploadImage.setImageURI(selectedImageUri)

        initUploadImage()
        btnContinueOnClick()
        btnBackOnClick()
        onBackPressed()
        checkTitle()
    }

    private fun checkTitle(){
        binding.etTitleArticle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    clearTitle()
                } else {
                    nullTitle()
                }
            }
        })
    }

    private fun isNullTitle(): Boolean{
        isNullTitle = if (binding.etTitleArticle.length() == 0){
            nullTitle()
            false
        } else {
            true
        }
        return isNullTitle
    }

    private fun nullTitle(): Boolean {
        binding.tilTitleArticle.error = getString(R.string.error_text_null_title)
        binding.etTitleArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
        return false
    }

    private fun clearTitle(){
        binding.tilTitleArticle.isErrorEnabled = false
        binding.etTitleArticle.setBackgroundResource(R.drawable.slr_outline_button_border)
    }


    @SuppressLint("ResourceAsColor")
    private fun validateGambar(): Boolean {
        return if (selectedImageUri == null){
            binding.tvErrorCover.text =  getString(R.string.error_text_null_cover)
            binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.RED)
            false
        } else {
            val checkSize = isImageFileSize(requireContext(), selectedImageUri!!)
            return if (!checkSize){
                binding.tvErrorCover.text = getString(R.string.error_text_max_size_image)
                binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.RED)
                false
            } else {
                val checkType = isImageFileType(selectedImageUri!!)
                if (!checkType){
                    binding.tvErrorCover.text = getString(R.string.error_text_image_type)
                    binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.RED)
                    false
                } else {
                    binding.tvErrorCover.text = ""
                    binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.TRANSPARENT)
                    true
                }
            }
        }
    }

    private fun initUploadImage() {
        binding.btnUploadImage.setOnClickListener {
            selectImageFromGallery(REQUEST_CODE_SELECT_IMAGE)
        }
    }

    private fun selectImageFromGallery(code: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(READ_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSIONS
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, code)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data!!
            if (selectedImageUri != null) {
                binding.ivUploadImage.setImageURI(selectedImageUri)
                validateGambar()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
            }
        }
    }

    private fun getImageFileSize(contentUri: Uri, context: Context): Long {
        var inputStream: InputStream? = null
        var size: Long = 0
        try {
            inputStream = context.contentResolver.openInputStream(contentUri)
            if (inputStream != null) {
                size = inputStream.available().toLong()
            }
        } catch (e: IOException) {
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
            }
        }
        return size
    }

    private fun isImageFileSize(context: Context, contentUri: Uri): Boolean {
        val fileSize = getImageFileSize(contentUri, context)
        return fileSize < 2000000L
    }

    private fun isImageFileType(uri: Uri): Boolean {
        val contentResolver = context?.contentResolver
        val type = contentResolver?.getType(uri)
        return type == "image/jpeg" || type == "image/png"
    }

    private fun btnBackOnClick() {
        binding.topAppBar.setNavigationOnClickListener {
            validationNull()
        }
    }

    private fun onBackPressed(){
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                validationNull()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this.viewLifecycleOwner, callback)
    }

    private fun validationNull(){
        if(!isNullTitle() && !validateGambar()) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        else findNavController().navigate(FragmentCreateArticleDirections.actionFragmentCreateArticleToCancelCreateArticle())
    }

    private fun btnContinueOnClick() {
        binding.btnContinue.setOnClickListener{
            validationTrue()
            validateGambar()
        }
    }

    private fun validationTrue(){
        if(isNullTitle() && validateGambar()) next()
    }

    private fun next(){
        val valuesTitle = binding.etTitleArticle.text.toString()
        val valuesThumbnail = selectedImageUri.toString()
        val action = FragmentCreateArticleDirections.actionToFragmentCreateArticle2(valuesTitle = valuesTitle, valuesThumbnail = valuesThumbnail)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}