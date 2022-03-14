package com.chinmay.horobook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.chinmay.horobook.R
import kotlinx.android.synthetic.main.fragment_horoscope.*
import java.util.*
import kotlin.collections.ArrayList

class HoroscopeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_horoscope, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = TimeZone.getAvailableIDs()
        val list = ArrayList<String>()
        for (i in options) {
            list.add(i)
        }
        val adapter = ArrayAdapter<String>(view.context,R.layout.support_simple_spinner_dropdown_item, list)

        myTimeZoneSpinner.adapter = adapter

        for (i in 0 until adapter.getCount()) {
            if (adapter.getItem(i)!!.contains("Kolkata",true)) {
                myTimeZoneSpinner.setSelection(i)
            }
        }




    }



}