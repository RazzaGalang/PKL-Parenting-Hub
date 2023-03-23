package com.example.pklparentinghub.ui.main.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentCreateArticleBinding

class FragmentCreateArticle : Fragment() {

    private var _binding: FragmentCreateArticleBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_IMAGE = 102

    private var isNullTitle = false
    var validCover = false
    var validTitle = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateArticleBinding.inflate(inflater, container, false)

        uploadImage()
        btnContinueOnClick()
        checkTitle()

        return binding.root
    }

    private fun btnContinueOnClick() {
        binding.btnContinue.setOnClickListener{
            validationTrue()
            nullCheck()
        }
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

    private fun validationTrue(){
        if(isNullTitle() && validTitle && validCover) binding()
    }

    private fun binding(){
        val fragment = FragmentCreateArticle2()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.containerCreateArticle, fragment)?.commit()
    }

    private fun nullCheck(){
        isNullTitle()
        if(!validCover){
            nullCover()
        } else clearCover()
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

    private fun nullCover(): Boolean{
        binding.tvErrorCover.text = getString(R.string.null_cover)
        return false
    }

    private fun clearTitle(){
        binding.tilTitleArticle.isErrorEnabled = false
        binding.etTitleArticle.setBackgroundResource(R.drawable.slr_outline_button_border)
        validTitle = true
    }

    private fun clearCover(){
        binding.tvErrorCover.text = ""
        validCover = true
    }

    private fun uploadImage() {
        binding.btnUploadImage.setOnClickListener {
            selectImageFromGallery()
        }
    }

    private fun selectImageFromGallery() {
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
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.ivUploadImage.setImageURI(selectedImageUri)
                binding.btnUploadImage.text = ""
                clearCover()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}