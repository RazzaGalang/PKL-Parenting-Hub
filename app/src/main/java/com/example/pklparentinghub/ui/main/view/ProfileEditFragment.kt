package com.example.pklparentinghub.ui.main.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions.getExtensionVersion
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentProfileEditBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import android.os.ext.SdkExtensions.getExtensionVersion

class ProfileEditFragment : Fragment() {

    private var _binding: FragmentProfileEditBinding? = null
    private val binding get() = _binding!!
    private lateinit var dateButton: Button
    private lateinit var dateText : TextView
    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_IMAGE = 102
    private val REQUEST_CODE_SELECT_PROFILE = 103

    private var isNullName = false
    private var isNullUsername = false
    private var isNullDate = false
    private var isFromBanner = false

    var validName = false
    var validUsername = false
    var validDate = false

    val fullNameRegex = "[A-Za-z '-]+"
    val minThreeCharRegex = "^.{3,}$"
    val minSixCharRegex = "^.{6,}$"
    val usernameRegex ="[a-zA-Z0-9._]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateButton = view.findViewById(R.id.editProfileButtonDate)
        dateText = view.findViewById(R.id.editProfileEditDate)

        dateButton.setOnClickListener {
            showDatePicker()
        }
        uploadImage()
        uploadProfile()
        username()
        back()
        name()
        submit()
        navBar()
        floatingBar()
//        photo()
    }

//    private fun photo(){
//        val pickMedia = registerForActivityResult(Pick()) { -> uri
//        if (uri != null){
//            Log.d("PhotoPicker", "Selected URI: $uri")
//        } else {
//            Log.d("PhotoPicker", "No media selected")
//        }}
//    }

    private fun navBar(){
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        view.visibility = View.GONE
    }

    private fun floatingBar(){
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.mainButtonCreateArticle)
        fab.visibility = View.GONE
    }

    private fun back(){
        binding.editProfileTopBar.setOnClickListener {
            val fragment = MainProfileFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
        }
    }

    private fun uploadImage() {
        binding.editProfileImageBanner.setOnClickListener {
//            selectImageFromGallery(REQUEST_CODE_SELECT_IMAGE)
            isFromBanner = true;
            handlePhotoPickerLaunch()
        }
    }

    private fun uploadProfile() {
        binding.editProfileImagePicture.setOnClickListener {
//            selectImageFromGallery(REQUEST_CODE_SELECT_PROFILE)
            handlePhotoPickerLaunch()
        }
    }

    // Registers a photo picker activity launcher in single-select mode.
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            if (isFromBanner) {
                binding.editProfileBanner.setImageURI(uri)
            }else{
                binding.editProfilePicture.setImageURI(uri)
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
        isFromBanner = false;
    }

    private fun isPhotoPickerAvailable(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    private fun handlePhotoPickerLaunch() {
        if (isPhotoPickerAvailable()) {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } else {
            if (isFromBanner) {
                selectImageFromGallery(REQUEST_CODE_SELECT_IMAGE)
                isFromBanner = false
            }else{
                selectImageFromGallery(REQUEST_CODE_SELECT_PROFILE)
            }

        }
    }

    private fun selectImageFromGallery(code: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
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
                binding.editProfileBanner.setImageURI(selectedImageUri)
            }
        }
        if (requestCode == REQUEST_CODE_SELECT_PROFILE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.editProfilePicture.setImageURI(selectedImageUri)
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
        binding.editProfileEditUsername.addTextChangedListener (object: TextWatcher {
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
        binding.editProfileEditFullName.addTextChangedListener(object: TextWatcher{
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
        binding.editProfileButtonSave.setOnClickListener {
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
        val fragment = MainProfileFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.frameLayoutMainActivity, fragment)?.commit()
    }

    private fun nullCheck(){
        isNullName()
        isNullUsername()
    }

    private fun isNullName(): Boolean{
        isNullName = if (binding.editProfileEditFullName.length() == 0){
            nullName()
            false
        } else {
            true
        }
        return isNullName
    }

    private fun isNullUsername(): Boolean{
        isNullUsername = if (binding.editProfileEditUsername.length() == 0){
            nullUsername()
            false
        } else {
            true
        }

        return isNullUsername
    }

    private fun regexMinFullName(){
        binding.editProfileInputFullName.error = getString(R.string.RegexFullName)
        validName = false
    }

    private fun regexFullName(){
        binding.editProfileInputFullName.error = getString(R.string.RegexName)
        validName = false
    }

    private fun regexFullUsername(){
        binding.editProfileInputUsername.error = getString(R.string.RegexUsername)
        validUsername = false
    }

    private fun regexMinFullUsername(){
        binding.editProfileInputUsername.error = getString(R.string.RegexFullUsername)
        validUsername = false
    }

    private fun nullName(): Boolean{
        binding.editProfileInputFullName.error = getString(R.string.ErrorName)
        return false
    }

    private fun nullUsername(): Boolean{
        binding.editProfileInputUsername.error = getString(R.string.ErrorUsername)
        return false
    }

    private fun clearName(){
        binding.editProfileInputFullName.isErrorEnabled = false
        validName = true
    }

    private fun clearUsername(){
        binding.editProfileInputUsername.isErrorEnabled = false
        validUsername = true
    }
}
