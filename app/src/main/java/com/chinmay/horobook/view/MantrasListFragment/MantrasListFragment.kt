package com.chinmay.horobook.view.MantrasListFragment

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
import com.chinmay.horobook.viewmodel.MantrasViewModel
import kotlinx.android.synthetic.main.fragment_mantras_list.*


class MantrasListFragment : Fragment() {


    private lateinit var viewModel: MantrasViewModel
    private val mantrasListAdapter = MantrasListAdapter(arrayListOf())

    val args : MantrasListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mantras_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MantrasViewModel::class.java)
        val albumId = args.albumId
        Log.d("albumId received","albumId received :" + albumId)
        viewModel.refreshMantrasList(albumId)

        mantrasListRecyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = mantrasListAdapter
        }

        refreshMantrasListLayout.setOnRefreshListener {
            listMantraError.visibility = View.GONE
            mantrasListRecyclerView.visibility = View.GONE
            viewModel.refreshMantrasList(albumId)
            loadingMantraView.visibility = View.VISIBLE
            refreshMantrasListLayout.isRefreshing =false

        }

        observeViewModel()
    }




    private fun observeViewModel() {
        viewModel.mantrasList.observe(viewLifecycleOwner, Observer { songs ->
            songs?.let{
                mantrasListRecyclerView.visibility = View.VISIBLE
                mantrasListAdapter.updateSongsList(songs)
                listMantraError.visibility = View.GONE
                loadingMantraView.visibility = View.GONE
            }
        })
        viewModel.mantrasListLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listMantraError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loadingMantras.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingMantraView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listMantraError.visibility = View.GONE
                    mantrasListRecyclerView.visibility = View.GONE
                }
            }

        })

    }
}