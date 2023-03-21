package com.example.pklparentinghub.ui.main.view

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.lifecycleScope
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentCompleteProfilePictureBinding
import com.example.pklparentinghub.utils.AccessManager

class CompleteProfilePictureFragment : Fragment() {

    private var _binding: FragmentCompleteProfilePictureBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_SELECT_PROFILE = 102

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
            val fragment = CompleteProfileBannerFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frameLayoutCompleteProfileActivity, fragment)?.commit()
        }
    }

    private fun initInsertPicture(){
        binding.completeProfilePictureInsertPicture.setOnClickListener {
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

        if (requestCode == REQUEST_CODE_SELECT_PROFILE && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                binding.completeProfilePictureInsertPicture.setImageURI(selectedImageUri)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}