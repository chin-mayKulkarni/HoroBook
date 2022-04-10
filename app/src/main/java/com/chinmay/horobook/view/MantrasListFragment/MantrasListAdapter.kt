package com.chinmay.horobook.view.MantrasListFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.horobook.R
import com.chinmay.horobook.model.LyricsListData
import kotlinx.android.synthetic.main.item_mantra.view.*

class MantrasListAdapter(val songsList: ArrayList<LyricsListData>) :
    RecyclerView.Adapter<MantrasListAdapter.SongViewHolder>() {

    lateinit var imageUrl : String

    fun updateSongsList(newSongsList: List<LyricsListData>, image_url: String) {
        imageUrl = image_url
        songsList.clear()
        songsList.addAll(newSongsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mantra, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.view.matra_name.text = songsList[position].songName
        holder.view.mantra_desc.text = songsList[position].songArtist
        holder.view.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    MantrasListFragmentDirections.actionTextFragment(
                        songsList[position].songLyrics.toString(),
                        songsList[position].songName.toString()
                    )
                )
            /*Toast.makeText(
                holder.view.context,
                "Clicked on " + songsList[position].songName,
                Toast.LENGTH_SHORT
            ).show()*/


        }
        /*if (songsList[position].songImageUrl!= null) {
            holder.view.imageView.loadImage(
                UrlConstants.media_url + songsList[position].songImageUrl,
                getProgressDrawable(holder.view.imageView.context)
            )
        } else {
            holder.view.imageView.loadImage(
                UrlConstants.media_url + imageUrl,
                getProgressDrawable(holder.view.imageView.context)
            )
        }*/
    }


    override fun getItemCount() = songsList.size

    class SongViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}