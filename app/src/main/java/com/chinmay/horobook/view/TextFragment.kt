package com.chinmay.horobook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chinmay.horobook.R
import kotlinx.android.synthetic.main.fragment_text.*


class TextFragment : Fragment() {

    var zoom_val = 25.0f

    val args: TextFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lyrics = args.lyrics

        mytv!!.textSize = zoom_val
        mytv!!.text = lyrics

        zoom_in.setOnClickListener {
            if (zoom_val < 55.0f)
                zoom_val += 5.0f
            mytv!!.textSize = zoom_val
        }

        zoom_out.setOnClickListener{
            if (zoom_val > 25.0f)
                zoom_val-=5.0f
            mytv!!.textSize = zoom_val
        }


    }
}