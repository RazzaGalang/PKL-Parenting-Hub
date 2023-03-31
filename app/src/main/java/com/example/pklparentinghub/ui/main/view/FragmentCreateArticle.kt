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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentCreateArticleBinding
import java.io.IOException
import java.io.InputStream

class FragmentCreateArticle : Fragment() {

    private var _binding: FragmentCreateArticleBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_IMAGE = 102

    private lateinit var values: String
    private var selectedImageUri: Uri? = null

    private var isNullTitle = false
    var validTitle = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticleBinding.inflate(inflater, container, false)

        initUploadImage()
        btnContinueOnClick()
        checkTitle()

        return binding.root
    }

    private fun binding(){
        val valuesThumbnail = selectedImageUri?.toString()!!
        val valuesTitle = binding.etTitleArticle.text.toString()
        val action = FragmentCreateArticleDirections.actionToFragmentCreateArticle2(valuesTitle, valuesThumbnail)
        findNavController().navigate(action)
    }

    private fun btnContinueOnClick() {
        binding.btnContinue.setOnClickListener{
            validationTrue()
            validateGambar()
        }
    }

    private fun validationTrue(){
        if(isNullTitle() && validTitle && validateGambar()) binding()
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
        binding.tilTitleArticle.error = getString(R.string.null_title)
        binding.etTitleArticle.setBackgroundResource(R.drawable.bg_white_red_outline)
        return false
    }

    private fun clearTitle(){
        binding.tilTitleArticle.isErrorEnabled = false
        binding.etTitleArticle.setBackgroundResource(R.drawable.slr_outline_button_border)
        validTitle = true
    }

    private fun initUploadImage() {
        binding.btnUploadImage.setOnClickListener {
            selectImageFromGallery(REQUEST_CODE_SELECT_IMAGE)
            //handlePhotoPickerLaunch()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun validateGambar(): Boolean {
        return if (selectedImageUri == null){
            binding.tvErrorCover.text =  getString(R.string.null_cover)
            binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.RED)
            false
        } else {
            val checkSize = isImageFileSize(requireContext(), selectedImageUri!!)
            return if (!checkSize){
                binding.tvErrorCover.text = "Size Gambar harus dibawah 2MB"
                binding.ivUploadImage.strokeColor = ColorStateList.valueOf(Color.RED)
                false
            } else {
                val checkType = isImageFileType(selectedImageUri!!)
                if (!checkType){
                    binding.tvErrorCover.text = "Tipe Gambar harus JPG/PNG"
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

    private fun isPhotoPickerAvailable(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    private fun handlePhotoPickerLaunch() {
        if (isPhotoPickerAvailable()) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}