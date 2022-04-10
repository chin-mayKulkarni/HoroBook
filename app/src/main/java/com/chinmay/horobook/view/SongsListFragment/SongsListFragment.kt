package com.chinmay.horobook.view.SongsListFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chinmay.horobook.R
import com.chinmay.horobook.util.displayPopUp
import com.chinmay.horobook.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_songs_list.*


class SongsListFragment : Fragment() {


    private lateinit var viewModel: ListViewModel
    private val songsListAdapter = SongsListAdapter(arrayListOf())


    val args: SongsListFragmentArgs by navArgs()
    lateinit var image_url : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_songs_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        val albumId = args.albumId
        image_url = args.imageUrl
        Log.d("albumId received","albumId received :" + albumId)
        viewModel.refreshSongsList(albumId)

        songsListRecyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = songsListAdapter
        }

        refreshSongsLayout.setOnRefreshListener {
            listSongsError.visibility = View.GONE
            songsListRecyclerView.visibility = View.GONE
            viewModel.refreshSongsList(albumId)
            loadingSongsView.visibility = View.VISIBLE
            refreshSongsLayout.isRefreshing =false

        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.songsList.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let{
                songsListRecyclerView.visibility = View.VISIBLE
                songsListAdapter.updateSongsList(songs, viewModel, image_url)
                listSongsError.visibility = View.GONE
                loadingSongsView.visibility = View.GONE
            }
        })
        viewModel.songsListLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listSongsError.visibility = if (it) View.VISIBLE else View.GONE
                if(isError) activity?.onBackPressed()
            }
        })

        viewModel.loadingSongs.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingSongsView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listSongsError.visibility = View.GONE
                    songsListRecyclerView.visibility = View.GONE
                }
            }

        })

        viewModel.showPopUp.observe(viewLifecycleOwner, Observer {
            it?.let {
                context?.let { context -> displayPopUp(context, "Sorry", it ) }
            }
        })

    }


}