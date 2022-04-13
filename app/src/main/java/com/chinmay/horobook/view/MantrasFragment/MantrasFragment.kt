package com.chinmay.horobook.view.MantrasFragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinmay.horobook.R
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.viewmodel.MantrasViewModel
import kotlinx.android.synthetic.main.fragment_mantras.*
import java.util.ArrayList


class MantrasFragment : Fragment() {


    private lateinit var viewModel: MantrasViewModel
    private val mantrasAlbumListAdapter = MantrasAlbumListAdapter(arrayListOf())

    private lateinit var mantrasListLocal: List<SongData>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mantras, container, false)
    }

    private val mantrasSearchTextWatcher : TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun afterTextChanged(s: Editable?) {
            filterList(s.toString())
        }
    }

    private fun filterList(text: String) {
        val temp: MutableList<SongData> = ArrayList()
        for (d in mantrasListLocal) {
            if (d.search_query!!.contains(text, true)) {
                temp.add(d)
            }
        }
        mantrasAlbumListAdapter.updateSearchList(temp)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MantrasViewModel::class.java)
        if (isInternetConnected()){
            viewModel.refreshMantras()
        } else showCustomDialogue()

        mantrasList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mantrasAlbumListAdapter

        }

        refreshMantrasLayout.setOnRefreshListener {
            mantrasError.visibility = View.GONE
            mantrasList.visibility = View.GONE
            mantras_search.text.clear()
            viewModel.refreshMantras()
            loadingMantrasView.visibility = View.VISIBLE
            refreshMantrasLayout.isRefreshing = false

        }

        mantras_search.isEnabled = false
        mantras_search.addTextChangedListener(mantrasSearchTextWatcher)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.mantras.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let{
                mantras_search.isEnabled = true
                mantrasList.visibility = View.VISIBLE
                mantrasListLocal = songs
                mantrasAlbumListAdapter.updateMantrasList(songs)
            }
        })
        viewModel.mantrasLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                mantrasError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingMantrasView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    mantrasError.visibility = View.GONE
                    mantrasList.visibility = View.GONE
                }
            }

        })
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            this.context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return wifiConnection != null && wifiConnection.isConnected || mobileConnection != null && mobileConnection.isConnected
    }


    private fun showCustomDialogue() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Please connect to internet to Proceed")?.setCancelable(false)
            ?.setTitle("No Internet")
            ?.setIcon(R.drawable.nointernet)?.setPositiveButton("Connect",
                DialogInterface.OnClickListener { dialog, which ->
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                })?.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->

                })
        val alert: AlertDialog = builder!!.create()
        alert.show()
    }


}