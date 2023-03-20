package com.example.pklparentinghub.ui.main.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pklparentinghub.databinding.FragmentCompleteProfileBiodataBinding
import java.util.*

class CompleteProfileBiodataFragment : Fragment() {

    private var _binding: FragmentCompleteProfileBiodataBinding? = null
    private val binding get() = _binding!!

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

        initNavigationButton()
        buttonDate()
    }

    private fun buttonDate (){

        binding.completeProfileBiodataButtonInputDate.setOnClickListener {
            showDatePicker()
        }

    }

    private fun initNavigationButton () {
        binding.completeProfileBiodataNavigationButton.setOnClickListener {
            val intentBiasa = Intent(this.context, MainActivity::class.java)
            startActivity(intentBiasa)
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
                val selectedDate = "$dayOfMonth/${monthOfYear+1}/$year"
                binding.completeProfileBiodataOutputDate.setText(" $selectedDate")
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

}