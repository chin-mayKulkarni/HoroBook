package com.chinmay.horobook.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chinmay.horobook.R
import com.chinmay.horobook.UrlConstants
import com.chinmay.horobook.util.SharedPreferencesHelper
import com.chinmay.horobook.view.DrawerNavigationFragments.WebActivity
import kotlinx.android.synthetic.main.fragment_horoscope.*
import java.util.*
import kotlin.collections.ArrayList


class HoroscopeFragment : Fragment() {


    private lateinit var day : String

    private lateinit var langSel : String

    private lateinit var zodaicSign : String
//    private var prefHelper = this.context?.let { SharedPreferencesHelper(it) }

    private lateinit var deviceID : String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_horoscope, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        err_horoscope.visibility = View.GONE

         var prefHelper = SharedPreferencesHelper(view.context)





        val list = ArrayList<String>()
        val dayList = ArrayList<String>()
        val zodaicList = ArrayList<String>()
        zodaicList.add("Aries")
        zodaicList.add("Taurus")
        zodaicList.add("Gemini")
        zodaicList.add("Cancer")
        zodaicList.add("Leo")
        zodaicList.add("Virgo")
        zodaicList.add("Libra")
        zodaicList.add("Scorpio")
        zodaicList.add("Sagittarius")
        zodaicList.add("Capricorn")
        zodaicList.add("Aquarius")
        zodaicList.add("Pisces")

        dayList.add("Today")
        dayList.add("Yesterday")
        dayList.add("Tomorrow")

        list.add("default")
        list.add("English")

        val adapter =
            ArrayAdapter<String>(view.context, R.layout.support_simple_spinner_dropdown_item, list)

        langSpinner.adapter = adapter


        daySpinner.adapter = ArrayAdapter<String>(view.context, R.layout.support_simple_spinner_dropdown_item, dayList)

        zodaicSpinner.adapter = ArrayAdapter<String>(view.context, R.layout.support_simple_spinner_dropdown_item, zodaicList)


        langSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.d(
                    "Horoscope fragment",
                    "Selected item is : " + langSpinner.adapter.getItem(position).toString()
                )
                langSel = langSpinner.adapter.getItem(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })

        daySpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.d(
                    "Horoscope fragment",
                    "Selected item is : " + daySpinner.adapter.getItem(position).toString()
                )
                day= daySpinner.adapter.getItem(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })

        zodaicSpinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Log.d(
                    "Horoscope fragment",
                    "Selected item is : " + zodaicSpinner.adapter.getItem(position).toString()
                )
                zodaicSign = zodaicSpinner.adapter.getItem(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        })

        btn_horoscope.setOnClickListener {
            if (langSel.equals(null) || zodaicSign.equals(null) || day.equals(null)){
                err_horoscope.visibility = View.VISIBLE
            } else{
                deviceID = prefHelper?.getAuthKey().toString()

                val intent = Intent(activity, WebActivity::class.java)
                intent.putExtra("url", UrlConstants.media_url + UrlConstants.horoscope + zodaicSign +"/" + day +"/" + langSel +"/" + deviceID)
                startActivity(intent)
            }
        }
    }
}

