package com.example.pklparentinghub

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.databinding.FragmentEditProfileBinding
import com.example.pklparentinghub.databinding.FragmentProfileBinding
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar

class FragmentEditProfile : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateButton: Button
    private lateinit var dateText : TextView
    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_IMAGE = 102
    private val REQUEST_CODE_SELECT_PROFILE = 103

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateButton = view.findViewById(R.id.btnDateEditProfile)
        dateText = view.findViewById(R.id.etEditDateProfile)

        dateButton.setOnClickListener {
            showDatePicker()
        }
        btnBack()
        uploadImage()
        uploadProfile()
    }

    private fun uploadImage() {
        binding.ivEditBanner.setOnClickListener {
            selectImageFromGallery(REQUEST_CODE_SELECT_IMAGE)
        }
    }

    private fun uploadProfile() {
        binding.ivEditProfile.setOnClickListener {
            selectImageFromGallery(REQUEST_CODE_SELECT_PROFILE)
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
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.ivBanner.setImageURI(selectedImageUri)
            }
        }
        if (requestCode == REQUEST_CODE_SELECT_PROFILE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.ivProfile.setImageURI(selectedImageUri)
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

    private fun btnBack(){
        binding.topAppBar.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_fragmentEditProfile_to_fragmentProfile)
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),{
                _, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear+1}/$year"
                dateText.text = " $selectedDate"
            },
        year,
        month,
        day
        )
        datePickerDialog.show()
    }
}