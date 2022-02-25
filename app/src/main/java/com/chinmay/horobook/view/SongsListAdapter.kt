package com.chinmay.horobook.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.horobook.R
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.util.getProgressDrawable
import com.chinmay.horobook.util.loadImage
import kotlinx.android.synthetic.main.item_song.view.*


class SongsListAdapter(val songsList: ArrayList<SongData>) : RecyclerView.Adapter<SongsListAdapter.SongViewHolder>(){

    fun updateSongsList(newSongsList : List<SongData>){
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
        holder.view.name.text = songsList[position].dogBreed
        holder.view.lifespan.text = songsList[position].lifeSpan
        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(SongsFragmentDirections.actionHoroscopeFragment())
        }
        holder.view.imageView.loadImage(songsList[position].imageUrl, getProgressDrawable(holder.view.imageView.context))
    }

    override fun getItemCount() = songsList.size

    class SongViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}