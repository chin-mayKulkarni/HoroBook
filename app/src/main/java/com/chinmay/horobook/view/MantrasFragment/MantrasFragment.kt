package com.chinmay.horobook.view.MantrasFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        // Inflate the layout for this fragment
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
        viewModel.refreshMantras()

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

        mantras_search.addTextChangedListener(mantrasSearchTextWatcher)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.mantras.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let{
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

}