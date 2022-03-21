package com.chinmay.horobook.view.SongsFragment

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
import com.chinmay.horobook.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_songs.*
import java.util.*


class SongsFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val songsAlbumListAdapter = SongsAlbumListAdapter(arrayListOf())

    private lateinit var songsListLocal: List<SongData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }

    private val searchTextChangeWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(s: Editable?) {
            filterList(s.toString())
        }
    }

    private fun filterList(text: String) {
        val temp: MutableList<SongData> = ArrayList()
        for (d in songsListLocal) {
            if (d.search_query!!.contains(text, true)) {
                temp.add(d)
            }
        }
        songsAlbumListAdapter.updateSearchList(temp)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_bar.clearFocus()

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        songsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = songsAlbumListAdapter

        }

        refreshLayout.setOnRefreshListener {
            listError.visibility = View.GONE
            songsList.visibility = View.GONE
            search_bar.text.clear()
            viewModel.refresh()
            loadingView.visibility = View.VISIBLE
            refreshLayout.isRefreshing = false

        }
        search_bar.addTextChangedListener(searchTextChangeWatcher)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.songs.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let {
                songsListLocal = songs
                songsList.visibility = View.VISIBLE
                songsAlbumListAdapter.updateSongsList(songs)
            }
        })
        viewModel.songsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    songsList.visibility = View.GONE
                }
            }
        })
    }


}