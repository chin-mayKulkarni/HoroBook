package com.chinmay.horobook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chinmay.horobook.R
import com.chinmay.horobook.util.displayPopUp

class MatchingFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.context?.let { displayPopUp(it, "Apologies", "We are still working on Match Making and will release this feature in upcoming releases") }
        activity?.onBackPressed()
        return inflater.inflate(R.layout.fragment_matching, container, false)
    }

}