package com.chinmay.horobook.view.MantrasListFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.horobook.R
import com.chinmay.horobook.UrlConstants
import com.chinmay.horobook.model.SongsListData
import com.chinmay.horobook.util.getProgressDrawable
import com.chinmay.horobook.util.loadImage
import kotlinx.android.synthetic.main.item_song.view.*

class MantrasListAdapter(val songsList: ArrayList<SongsListData>) :
    RecyclerView.Adapter<MantrasListAdapter.SongViewHolder>() {

    fun updateSongsList(newSongsList: List<SongsListData>) {
        songsList.clear()
        songsList.addAll(newSongsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.view.name.text = songsList[position].songName
        holder.view.lifespan.text = songsList[position].songArtist
        holder.view.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(
                    /*MantrasListFragmentDirections.actionPlayerFragment(songsList[position].songUrl.toString(),
                    songsList[position].songImageUrl.toString(), songsList[position].songName.toString()))*/
            MantrasListFragmentDirections.actionTextFragment())
            Toast.makeText(
                holder.view.context,
                "Clicked on " + songsList[position].songName,
                Toast.LENGTH_SHORT
            ).show()


        }
        holder.view.imageView.loadImage(
            UrlConstants.media_url + songsList[position].songImageUrl,
            getProgressDrawable(holder.view.imageView.context)
        )
    }


    override fun getItemCount() = songsList.size

    class SongViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}