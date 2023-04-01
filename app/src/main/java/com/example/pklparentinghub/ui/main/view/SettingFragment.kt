package com.example.pklparentinghub.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        initOnClick()
        initItemView()
    }

    private fun initItemView() {
        binding.apply {
            pusatBantuan.tvItem.text = "Pusat Bantuan"
            aboutParentingHub.tvItem.text = "Tentang Parenting Hub"
            kebijakanPrivasi.tvItem.text = "Kebijakan Privasi"
            syaratDanKetentuan.tvItem.text = "Syarat dan Ketentuan"
            keluar.tvItem.text = "Keluar"

            keluar.ivItem.setImageResource(R.drawable.ic_log_out)
            keluar.tvItem.setTextColor(requireContext().getColor(R.color.error30))
        }
    }

    private fun initOnClick() {
        binding.apply {
            pusatBantuan.clItem.setOnClickListener {
                setupToDetailSetting("pusatBantuan")
            }

            aboutParentingHub.clItem.setOnClickListener {
                setupToDetailSetting("about")
            }

            kebijakanPrivasi.clItem.setOnClickListener {
                setupToDetailSetting("privasi")
            }

            syaratDanKetentuan.clItem.setOnClickListener {
                setupToDetailSetting("syarat")
            }

            keluar.clItem.setOnClickListener {
                findNavController().navigate(SettingFragmentDirections.actionToLogoutPopFragment())
            }

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun setupToDetailSetting(requestDetail : String){
        findNavController().navigate(SettingFragmentDirections.actionSettingFragmentToSettingDetailFragment(requestDetail))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}