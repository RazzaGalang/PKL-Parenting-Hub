package com.example.pklparentinghub.ui.main.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.databinding.FragmentCompleteProfilePictureBinding
import java.io.IOException
import java.io.InputStream

class CompleteProfilePictureFragment : Fragment() {

    private var _binding: FragmentCompleteProfilePictureBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_PROFILE = 102

    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteProfilePictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInsertPicture()
        initNavigation()
    }

    private fun initNavigation(){
        binding.completeProfilePictureNavigationButton.setOnClickListener {
            if (validateGambar())
                setupStoreProfilePicture()
            else
                validateGambar()
        }
    }

    private fun initInsertPicture(){
        binding.completeProfilePictureInsertPicture.setOnClickListener {
            selectImageFromGallery(REQUEST_CODE_SELECT_PROFILE)
        }
    }

    private fun setupStoreProfilePicture(){
        val profilePicture = selectedImageUri.toString()

        val action = CompleteProfilePictureFragmentDirections.actionCompleteProfilePictureFragmentToCompleteProfileBannerFragment(profilePicture)
        findNavController().navigate(action)
    }

    private fun validateGambar(): Boolean {
        return if (selectedImageUri == null){
            binding.completeProfilePictureErrorText.text = "Harus menyertakan Gambar"
            false
        } else {
            val checkSize = isImageFileSize(requireContext(), selectedImageUri!!)
            return if (!checkSize){
                binding.completeProfilePictureErrorText.text = "Size Gambar harus dibawah 2MB"
                false
            } else {
                val checkType = isImageFileType(selectedImageUri!!)
                if (!checkType){
                    binding.completeProfilePictureErrorText.text = "Tipe Gambar harus JPG/PNG"
                    false
                } else {
                    binding.completeProfilePictureErrorText.text = ""
                    true
                }
            }
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

        if (requestCode == REQUEST_CODE_SELECT_PROFILE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data!!
            if (selectedImageUri != null) {
                binding.completeProfilePictureInsertPicture.setImageURI(selectedImageUri)
                validateGambar()
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
                startActivityForResult(intent, REQUEST_CODE_SELECT_PROFILE)
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

    private fun getImageFileSize(contentUri: Uri, context: Context): Long {
        var inputStream: InputStream? = null
        var size: Long = 0
        try {
            inputStream = context.contentResolver.openInputStream(contentUri)
            if (inputStream != null) {
                size = inputStream.available().toLong()
            }
        } catch (e: IOException) {
            // handle exception
        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                // handle exception
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