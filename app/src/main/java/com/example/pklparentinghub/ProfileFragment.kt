package com.example.pklparentinghub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileFragment : Fragment() {

    private lateinit var adapter: ProfileAdapter
    private lateinit var profileArrayList: ArrayList<ProfileModel>
    private lateinit var recyclerView: RecyclerView

    lateinit var profile : Array<Int>
    lateinit var username : Array<String>
    lateinit var desc : Array<String>
    lateinit var icLike : Array<Int>
    lateinit var like : Array<String>
    lateinit var date : Array<String>
    lateinit var default : Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rvArtichle)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ProfileAdapter(profileArrayList)
        recyclerView.adapter = adapter
    }

    private fun dataInitialize(){

        profileArrayList = arrayListOf<ProfileModel>()

        profile = arrayOf(
            R.drawable.img_profile,
            R.drawable.img_prof,
            R.drawable.img_profile,
            R.drawable.img_prof
        )

        username = arrayOf(
            getString(R.string.User),
            getString(R.string.User),
            getString(R.string.User),
            getString(R.string.User)
        )

        desc = arrayOf(
            getString(R.string.desc_art),
            getString(R.string.desc_art),
            getString(R.string.desc_art),
            getString(R.string.desc_art)
        )

        icLike = arrayOf(
            R.drawable.ic_like,
            R.drawable.ic_like,
            R.drawable.ic_like,
            R.drawable.ic_like
        )

        like = arrayOf(
            getString(R.string.Like),
            getString(R.string.Like),
            getString(R.string.Like),
            getString(R.string.Like),
        )

        date = arrayOf(
            getString(R.string.Date),
            getString(R.string.Date),
            getString(R.string.Date),
            getString(R.string.Date)
        )

        default = arrayOf(
            R.drawable.img_default,
            R.drawable.img_default,
            R.drawable.img_default,
            R.drawable.img_default
        )

        for (i in profile.indices){

            val profile = ProfileModel(profile[i], username[i], desc[i], icLike[i], like[i], date[i], default[i])
            profileArrayList.add(profile)
        }
    }
}