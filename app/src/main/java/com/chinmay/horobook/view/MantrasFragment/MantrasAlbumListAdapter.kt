package com.chinmay.horobook.view.MantrasFragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.horobook.R
import com.chinmay.horobook.UrlConstants
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.util.getProgressDrawable
import com.chinmay.horobook.util.loadImage
import kotlinx.android.synthetic.main.item_song.view.*
import java.io.IOException

class MantrasAlbumListAdapter(val mantrasList: ArrayList<SongData>) :
    RecyclerView.Adapter<MantrasAlbumListAdapter.MantraViewHolder>() {

    fun updateMantrasList(newMantrasList: List<SongData>) {
        mantrasList.clear()
        mantrasList.addAll(newMantrasList)
        notifyDataSetChanged()
    }

    fun updateSearchList(newSongsList: List<SongData>){
        mantrasList.clear()
        mantrasList.addAll(newSongsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MantraViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_song, parent, false)
        return MantraViewHolder(view)
    }

    override fun onBindViewHolder(holder: MantraViewHolder, position: Int) {
        holder.view.name.text = mantrasList[position].dogBreed
        holder.view.lifespan.text = mantrasList[position].lifeSpan
        holder.view.setOnClickListener {
            // playAudio(holder)
            val action =
                MantrasFragmentDirections.actionMantrasListFragment(mantrasList[position].breedId.toString())
            Navigation.findNavController(it)
                .navigate(action)
            Toast.makeText(
                holder.view.context,
                "Clicked on " + mantrasList[position].dogBreed,
                Toast.LENGTH_SHORT
            ).show()


        }
        holder.view.imageView.loadImage(
            UrlConstants.media_url + mantrasList[position].imageUrl,
            getProgressDrawable(holder.view.imageView.context)
        )
    }

    private fun playAudio(holder: MantraViewHolder) {
        val url =
            "https://chandusanjith.pythonanywhere.com/media/mantra_image/Scam_1992_Theme_Official_-_Achint_1.mp3"
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            Toast.makeText(holder.view.context, "Song is paused", Toast.LENGTH_SHORT).show()
        } else {

            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            Toast.makeText(holder.view.context, "Song is playing", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount() = mantrasList.size

    class MantraViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}