package com.example.pklparentinghub.ui.main.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.data.api.ApiHelper
import com.example.pklparentinghub.data.api.RetrofitBuilder
import com.example.pklparentinghub.data.model.userDetail.CompleteProfileRequest
import com.example.pklparentinghub.databinding.FragmentCompleteProfileBiodataBinding
import com.example.pklparentinghub.ui.base.ProfileViewModelFactory
import com.example.pklparentinghub.ui.base.StoreFileUserViewModelFactory
import com.example.pklparentinghub.ui.base.UpdateUserViewModelFactory
import com.example.pklparentinghub.ui.main.viewmodel.ProfileViewModel
import com.example.pklparentinghub.ui.main.viewmodel.StoreFileUserViewModel
import com.example.pklparentinghub.ui.main.viewmodel.UpdateUserViewModel
import com.example.pklparentinghub.utils.AccessManager
import com.example.pklparentinghub.utils.Status
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

class CompleteProfileBiodataFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBiodataBinding? = null
    private val binding get() = _binding!!

    private lateinit var requestFileProfilePicture : MultipartBody.Part
    private lateinit var requestFileBannerPicture : MultipartBody.Part

    private val emptyString = ""
    private lateinit var valuesUsername : String
    private lateinit var valuesFullname : String
    private lateinit var valuesBirthday : String
    private var valuesProfilePicture : String = ""
    private var valuesBannerPicture : String = ""
    private val valuesGender
        get() = if (binding.rbMale.isChecked)
            "L"
        else if (binding.rbFemale.isChecked)
            "P"
        else
            ""

    private lateinit var viewModel: UpdateUserViewModel
    private lateinit var viewModelDetailUser : ProfileViewModel
    private lateinit var viewModelStoreFileUser : StoreFileUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompleteProfileBiodataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSubmitButton()
        initDatePickerButton()
        initGetUserDetail()
        setupViewModel()
        setupObserve()
        initView()
    }

    private fun initView() {
        binding.apply {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initSubmitButton () {
        binding.completeProfileBiodataNavigationButton.setOnClickListener {
            if (isDateError()) {
                isDateError()
            } else {
                setupSubmitData()
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun isDateError() : Boolean{
        binding.apply {
            return if (completeProfileBiodataOutputDate.text.toString() == emptyString){
                completeProfileBiodataButtonInputDate.setStrokeColorResource(R.color.error30)
                completeProfileBiodataButtonInputDate.setTextColor(requireContext().getColor(R.color.error30))
                true
            } else {
                completeProfileBiodataButtonInputDate.setStrokeColorResource(R.color.primary50)
                completeProfileBiodataButtonInputDate.setTextColor(requireContext().getColor(R.color.primary50))
                false
            }
        }
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(
            this,
            UpdateUserViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[UpdateUserViewModel::class.java]

        viewModelDetailUser = ViewModelProvider(
            this,
            ProfileViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[ProfileViewModel::class.java]

        viewModelStoreFileUser = ViewModelProvider(
            this,
            StoreFileUserViewModelFactory(ApiHelper(RetrofitBuilder.getRetrofit()))
        )[StoreFileUserViewModel::class.java]

    }

    private fun initGetUserDetail(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    AccessManager(requireContext())
                        .accessUserId
                        .collect { userId ->
                            viewModelDetailUser.requestProfile(token, userId).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                                it?.let { resource ->
                                    when (resource.status){
                                        Status.SUCCESS -> {
                                            resource.data?.let { profile ->
                                                valuesFullname = profile.body()?.data?.fullName.toString()
                                                valuesUsername = profile.body()?.data?.username.toString()
                                            }
                                        }
                                        Status.ERROR -> {

                                        }
                                        Status.LOADING -> {

                                        }
                                    }
                                }
                            })
                        }
                }
        }
    }
    private fun setupSubmitData(){
        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    AccessManager(requireContext())
                        .accessUserId
                        .collect { userId ->
                            viewModel.updateUser(token, userId, CompleteProfileRequest(
                                valuesFullname,
                                valuesUsername,
                                valuesProfilePicture,
                                valuesBannerPicture,
                                binding.completeProfileBiodataInputBiodata.text.toString(),
                                valuesGender,
                                valuesBirthday
                            ))
                        }
                }
        }
    }

    private fun setupObserve(){
        setupObserveUpdate()
        setupObserveStoreFile()
    }
    private fun setupObserveUpdate(){
        viewModel.updateUserResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        Log.e(TAG, "setupSubmitData: UPDATE SUCCESS" )
                        findNavController().navigate(CompleteProfileBiodataFragmentDirections.actionCompleteProfileBiodataFragmentToMainActivity())
                        requireActivity().finish()
                    }
                    Status.ERROR -> {
                        Log.e(TAG, "setupSubmitData: UPDATE ERROR" )
                    }
                    Status.LOADING -> {
                        Log.e(TAG, "setupSubmitData: UPDATE LOADING" )
                    }
                }
            }
        })
    }

    private fun setupObserveStoreFile(){
        viewModelStoreFileUser.storeUserFileResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let { resource ->
                when (resource.status){
                    Status.SUCCESS -> {
                        it.let { item ->
                            Log.e(TAG, "setupObserveStoreFile: STORE IMAGE SUCCESS")
                            if (valuesProfilePicture == ""){
                                valuesProfilePicture = item.data?.body()?.data?.filename!!
                                setupBannerPicturePath()
                                Log.e(TAG, "setupObserveStoreFile: VALUES PROFILE PICTURE STORED")
                            } else {
                                valuesBannerPicture = item.data?.body()?.data?.filename!!
                                setupSubmitData()
                                Log.e(TAG, "setupObserveStoreFile: VALUES BANNER PICTURE STORED")
                            }
                        }
                    }
                    Status.ERROR -> {
                        Log.e(TAG, "setupObserveStoreFile: STORE IMAGE ERROR")
                    }
                    Status.LOADING -> {
                        Log.e(TAG, "setupObserveStoreFile: STORE IMAGE LOADING")
                    }
                }
            }
        })
    }

    private fun setupProfilePicturePath(){
        val profilePicture = arguments?.getString("profilePicture")?.toUri()

        val fileProfilePicture = File(profilePicture?.let { getPathFromMediaStore(requireContext(), it) }.toString())

        val requestProfilePicture = RequestBody.create("image/png".toMediaTypeOrNull(), fileProfilePicture)

        requestFileProfilePicture = MultipartBody.Part.createFormData("image", fileProfilePicture.name, requestProfilePicture)

        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModelStoreFileUser.storeUserFile(token, requestFileProfilePicture)
                }
        }
    }
    private fun setupBannerPicturePath(){
        val bannerPicture = arguments?.getString("bannerPicture")?.toUri()

        val fileBannerPicture = File(bannerPicture?.let { getPathFromMediaStore(requireContext(), it) }.toString())

        val requestBannerPicture = RequestBody.create("image/png".toMediaTypeOrNull(), fileBannerPicture)

        requestFileBannerPicture = MultipartBody.Part.createFormData("image", fileBannerPicture.name, requestBannerPicture)

        lifecycleScope.launchWhenResumed {
            AccessManager(requireContext())
                .access
                .collect { token ->
                    viewModelStoreFileUser.storeUserFile(token, requestFileBannerPicture)
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

    private fun initDatePickerButton (){
        binding.completeProfileBiodataButtonInputDate.setOnClickListener {
            showDatePicker()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),{
                    _, year, monthOfYear, dayOfMonth ->
                valuesBirthday = "$dayOfMonth-${monthOfYear+1}-$year"
                val outputBirthday = "$dayOfMonth/${monthOfYear+1}/$year"
                binding.completeProfileBiodataOutputDate.setText(" $outputBirthday")
                isDateError()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}