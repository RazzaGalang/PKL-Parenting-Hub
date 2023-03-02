package com.example.pklparentinghub

import android.app.DatePickerDialog
import android.media.Image
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateButton = view.findViewById(R.id.btnDateEditProfile)
        dateText = view.findViewById(R.id.tvShowDateEditProfil)

        dateButton.setOnClickListener {
            showDatePicker()
        }
        btnBack()

    }

    private fun btnBack(){
        view?.findViewById<ShapeableImageView>(R.id.icBack)?.setOnClickListener{
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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentEditProfileBinding.bind(view)
//
//        binding.apply {
//            btnDateEditProfile.setOnClickListener{
//                val datePicker = FragmentDatePicker()
//                val supportFragmentManager = requireActivity().supportFragmentManager
//
//                supportFragmentManager.setFragmentResultListener(
//                    "REQUEST_KEY", viewLifecycleOwner
//                ){
//                    resultKey, bundle -> if (resultKey == "REQUEST_KEY"){
//                        val date = bundle.getString("SELECTED_DATE")
//                        tvShowDateEditProfil.text = date
//                }
//                }
//            }
//        }
//    }
}