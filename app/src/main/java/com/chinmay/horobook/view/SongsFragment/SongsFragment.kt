package com.chinmay.horobook.view.SongsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinmay.horobook.R
import com.chinmay.horobook.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_songs.*


class SongsFragment : Fragment() {

    private lateinit var viewModel : ListViewModel
    private val songsAlbumListAdapter = SongsAlbumListAdapter(arrayListOf())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_songs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        songsList.apply {
            layoutManager =LinearLayoutManager(context)
            adapter = songsAlbumListAdapter

        }

        refreshLayout.setOnRefreshListener {
            listError.visibility = View.GONE
            songsList.visibility = View.GONE
            viewModel.refresh()
            loadingView.visibility = View.VISIBLE
            refreshLayout.isRefreshing = false

        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.songs.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let{
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