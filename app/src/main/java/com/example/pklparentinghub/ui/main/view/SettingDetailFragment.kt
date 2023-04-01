package com.example.pklparentinghub.ui.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pklparentinghub.R
import com.example.pklparentinghub.databinding.FragmentSettingDetailBinding

class SettingDetailFragment : Fragment() {

    private var _binding: FragmentSettingDetailBinding? = null
    private val binding get() = _binding!!

    private var  requestDetail = "emptyString"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestDetail = arguments?.getString("requestDetail").toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        when(requestDetail){
            "pusatBantuan" -> setupPusatBantuan()

            "about" -> setupAboutParentingHub()

            "privasi" -> setupPrivasi()

            "syarat" -> setupSyaratdanKetentuan()

            else -> {}
        }
    }

    private fun setupPusatBantuan(){
        binding.svOnlyText.isVisible = false
        binding.clImage.isVisible = true

        binding.tvTitle.text = "Pusat Bantuan"
        binding.ivImage.setImageResource(R.drawable.img_condition_family_white)
        binding.tvContentImage.text = "Temukan solusi terbaik untuk peran sebagai orang tua dengan bantuan Parenting Hub. Hubungi kami jika mengalami kesulitan dengan aplikasi."

        binding.btWhatsapp.isVisible = true
        binding.btEmail.isVisible = true
        binding.tvAboutTeam.isVisible = false
        binding.tvAboutDescTeam.isVisible = false

        binding.btWhatsapp.setOnClickListener {
            val webpageUrl = "http://wa.me/6281774128129"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webpageUrl))
            startActivity(intent)
        }

        binding.btEmail.setOnClickListener {
            val webpageUrl = "https://mail.google.com/mail/u/0/?view=cm&tf=1&fs=1&to=razzagalang45@gmail.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webpageUrl))
            startActivity(intent)
        }
    }

    private fun setupAboutParentingHub(){
        binding.svOnlyText.isVisible = false
        binding.clImage.isVisible = true

        binding.tvTitle.text = "Tentang Parenting Hub"
        binding.ivImage.setImageResource(R.drawable.img_auth_login_main_illustration)
        binding.tvContentImage.text = "Parenting Hub adalah sebuah aplikasi yang menyediakan berbagai informasi, saran, dan solusi untuk para orang tua dalam menghadapi tantangan dalam membesarkan anak. Aplikasi ini sangat interaktif dan mudah digunakan, sehingga para pengguna dapat dengan mudah menemukan konten yang relevan dan bermanfaat untuk memenuhi kebutuhan mereka sebagai orang tua. \n"

        binding.btWhatsapp.isVisible = false
        binding.btEmail.isVisible = false
        binding.tvAboutTeam.isVisible = true
        binding.tvAboutDescTeam.isVisible = true
    }

    private fun setupPrivasi(){
        binding.svOnlyText.isVisible = true
        binding.clImage.isVisible = false

        binding.tvTitle.text = "Kebijakan Privasi"
        binding.tvContent.text = "Kami di Aplikasi Parenting Hub: Artikel Dunia Parenting, menghargai privasi pengguna kami dan berkomitmen untuk melindungi informasi pribadi mereka dengan cara yang bertanggung jawab. Kebijakan Privasi ini menjelaskan bagaimana kami mengumpulkan, menggunakan, dan melindungi informasi yang dikumpulkan dari pengguna aplikasi kami.\n\nInformasi yang Kami Kumpulkan Kami dapat mengumpulkan informasi pribadi seperti nama, alamat email, nomor telepon, dan informasi kontak lainnya yang diberikan oleh pengguna saat mendaftar akun di aplikasi kami. Kami juga dapat mengumpulkan informasi non-pribadi seperti lokasi, jenis perangkat, dan data penggunaan aplikasi kami.\n\nBagaimana Kami Menggunakan Informasi Kami menggunakan informasi yang kami kumpulkan untuk menyediakan layanan aplikasi kami, untuk menghubungi pengguna terkait dengan pertanyaan atau permintaan yang diajukan, untuk memperbaiki aplikasi kami, dan untuk menyediakan informasi dan konten yang relevan untuk pengguna. Kami juga dapat menggunakan informasi yang dikumpulkan untuk tujuan pemasaran dan promosi, namun kami tidak akan pernah menjual informasi pengguna kami kepada pihak ketiga.\n\nBagaimana Kami Melindungi Informasi Kami mengambil langkah-langkah yang sesuai untuk melindungi informasi pribadi pengguna kami dari akses yang tidak sah, perubahan, pengungkapan, atau penghapusan yang tidak sah. Kami menggunakan sistem keamanan seperti enkripsi data dan akses terbatas ke informasi pribadi pengguna kami.\n\nAkses dan Pengendalian Informasi Pribadi Pengguna dapat mengakses atau memperbarui informasi pribadi mereka yang tersimpan di aplikasi kami dengan masuk ke akun mereka. Jika pengguna ingin menghapus akun mereka atau meminta penghapusan informasi pribadi mereka dari sistem kami, mereka dapat menghubungi kami melalui email yang tersedia di aplikasi kami.\n\nPersetujuan Dengan menggunakan Aplikasi Parenting Hub: Artikel Dunia Parenting, pengguna menyetujui Kebijakan Privasi ini. Jika pengguna tidak setuju dengan Kebijakan Privasi kami, kami meminta mereka untuk tidak menggunakan aplikasi kami.\n\nPerubahan pada Kebijakan Privasi Kami dapat memperbarui Kebijakan Privasi kami dari waktu ke waktu. Jika kami melakukan perubahan yang signifikan pada kebijakan ini, kami akan memberitahukan pengguna dengan memposting perubahan tersebut di aplikasi kami atau melalui email yang tersedia di aplikasi kami.\n\nHubungi Kami Jika pengguna memiliki pertanyaan atau kekhawatiran tentang Kebijakan Privasi kami atau penggunaan aplikasi kami, mereka dapat menghubungi kami melalui email yang tersedia di aplikasi kami.\n\nTerima kasih telah menggunakan Aplikasi Parenting Hub: Artikel Dunia Parenting.\n"
    }

    private fun setupSyaratdanKetentuan(){
        binding.svOnlyText.isVisible = true
        binding.clImage.isVisible = false

        binding.tvTitle.text = "Syarat dan Ketentuan"
        binding.tvContent.text = "Syarat dan Ketentuan Aplikasi Parenting Hub: Artikel Dunia Parenting\n\nSelamat datang di Aplikasi Parenting Hub: Artikel Dunia Parenting! Harap baca syarat dan ketentuan kami dengan cermat sebelum menggunakan aplikasi kami. Dengan menggunakan aplikasi ini, pengguna menyetujui dan terikat dengan syarat dan ketentuan berikut:\n\n1. Layanan yang Disediakan Aplikasi Parenting Hub: Artikel Dunia Parenting menyediakan platform untuk pengguna untuk membuat dan membaca artikel tentang dunia parenting. Aplikasi ini juga dapat menyimpan informasi pribadi pengguna seperti nama, tanggal lahir, dan alamat email. Kami tidak menjamin bahwa aplikasi ini akan berfungsi tanpa gangguan atau kesalahan, dan kami berhak untuk memperbarui atau mengubah aplikasi ini dari waktu ke waktu.\n\n2. Kewajiban Pengguna Pengguna bertanggung jawab untuk mematuhi syarat dan ketentuan ini dan semua hukum yang berlaku terkait dengan penggunaan aplikasi ini. Pengguna bertanggung jawab atas segala konten yang mereka buat atau unggah di aplikasi kami, dan mereka harus memastikan bahwa konten tersebut mematuhi semua hukum dan peraturan yang berlaku.\n\n3. Informasi Pribadi Kami menyimpan informasi pribadi pengguna seperti nama, tanggal lahir, dan alamat email untuk menyediakan layanan kami. Kami mematuhi semua undang-undang yang berlaku terkait privasi data dan akan melindungi informasi pribadi pengguna kami sebaik mungkin. Kami tidak akan pernah menjual informasi pribadi pengguna kami kepada pihak ketiga.\n\n4. Konten Pengguna Pengguna bertanggung jawab atas segala konten yang mereka buat atau unggah di aplikasi kami. Kami berhak untuk menghapus atau membatasi akses ke konten yang melanggar syarat dan ketentuan kami atau hukum yang berlaku.\n\n5. Kepemilikan Konten Pengguna tetap memiliki kepemilikan atas konten yang mereka buat atau unggah di aplikasi kami. Namun, pengguna memberikan hak kepada kami untuk menggunakan konten tersebut untuk menyediakan layanan kami.\n\n6. Pelanggaran Syarat dan Ketentuan Kami berhak untuk menangguhkan atau menghentikan akses pengguna ke aplikasi kami jika pengguna melanggar syarat dan ketentuan kami atau hukum yang berlaku.\n\n7. Perubahan Syarat dan Ketentuan Kami dapat memperbarui syarat dan ketentuan kami dari waktu ke waktu. Jika kami melakukan perubahan yang signifikan pada syarat dan ketentuan ini, kami akan memberitahukan pengguna dengan memposting perubahan tersebut di aplikasi kami atau melalui email yang tersedia di aplikasi kami.\n\n8. Kontak Kami Jika pengguna memiliki pertanyaan atau kekhawatiran tentang syarat dan ketentuan kami atau penggunaan aplikasi kami, mereka dapat menghubungi kami melalui email yang tersedia di aplikasi kami.\n\nTerima kasih telah menggunakan Aplikasi Parenting Hub: Artikel Dunia Parenting."
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}