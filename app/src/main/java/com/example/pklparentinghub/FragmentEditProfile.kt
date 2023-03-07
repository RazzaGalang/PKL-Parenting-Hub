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
import androidx.core.widget.addTextChangedListener
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

    private var isNullName = false
    private var isNullUsername = false
    private var isNullDate = false

    var validName = false
    var validUsername = false
    var validDate = false

    val fullNameRegex = "[A-Za-z '-]+"
    val minThreeCharRegex = "^.{3,}$"
    val minSixCharRegex = "^.{6,}$"
    val usernameRegex ="[a-zA-Z0-9._]+"

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
        username()
        name()
        submit()
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

    private fun username(){
        binding.etUsernameEditProfile.addTextChangedListener (object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    nullUsername()
                } else if (!(s.toString().matches(usernameRegex.toRegex()))){
                    regexFullUsername()
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))){
                    regexMinFullUsername()
                } else {
                    clearUsername()
                }

            }
        })
    }

    private fun name(){
        binding.etNameEditProfile.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)){
                    nullName()
                } else if (!(s.toString().matches(fullNameRegex.toRegex()))){
                    regexFullName()
                } else if (!(s.toString().matches(minThreeCharRegex.toRegex()))){
                    regexMinFullName()
                } else {
                    clearName()
                }
            }
        })
    }

    private fun submit(){
        binding.btnSaveEditProfile.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        nullCheck()
        validationTrue()
    }

    private fun validationTrue() {
        if(isNullName() && isNullUsername() && validName==true && validUsername==true)
            binding()
    }

    private fun binding(){
        view?.findViewById<Button>(R.id.btnSaveEditProfile)?.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentEditProfile_to_fragmentProfile)
        }
    }
    private fun nullCheck(){
        isNullName()
        isNullUsername()
    }

    private fun isNullName(): Boolean{
        isNullName = if (binding.etNameEditProfile.length() == 0){
            nullName()
            false
        } else {
            true
        }
        return isNullName
    }

    private fun isNullUsername(): Boolean{
        isNullUsername = if (binding.etUsernameEditProfile.length() == 0){
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }

    private fun regexMinFullName(){
        binding.inputNameEditText.error = getString(R.string.RegexFullName)
        validName = false
    }

    private fun regexFullName(){
        binding.inputNameEditText.error = getString(R.string.RegexName)
        validName = false
    }

    private fun regexFullUsername(){
        binding.inputUserEditProfile.error = getString(R.string.RegexUsername)
        validUsername = false
    }

    private fun regexMinFullUsername(){
        binding.inputUserEditProfile.error = getString(R.string.RegexFullUsername)
        validUsername = false
    }

    private fun nullName(): Boolean{
        binding.inputNameEditText.error = getString(R.string.ErrorName)
        return false
    }

    private fun nullUsername(): Boolean{
        binding.inputUserEditProfile.error = getString(R.string.ErrorUsername)
        return false
    }

    private fun clearName(){
        binding.inputNameEditText.isErrorEnabled = false
        validName = true
    }

    private fun clearUsername(){
        binding.inputUserEditProfile.isErrorEnabled = false
        validUsername = true
    }
}