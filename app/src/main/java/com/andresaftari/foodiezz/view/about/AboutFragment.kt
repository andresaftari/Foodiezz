package com.andresaftari.foodiezz.view.about

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andresaftari.foodiezz.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_about, container, false)

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the textview with my contact
        tv_nameAbout.text = "PRASIDYA PRAMADRESANA SAFTARI"
        tv_linkedIn.text = "https://www.linkedin.com/in/prasidya-pramadresana-saftari/"
        tv_emailAbout.text = "andresaftari@gmail.com"
        tv_contactAbout.text = "instagram.com/andresaftari"

        // Redirect the user to my LinkedIn page
        tv_linkedIn.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW).setData(
                    Uri.parse("https://www.linkedin.com/in/prasidya-pramadresana-saftari/")
                )
            )
        }

        // Redirect the user to my Instagram profile
        tv_contactAbout.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW).setData(
                    Uri.parse("https://www.instagram.com/andresaftari/")
                )
            )
        }
    }
}