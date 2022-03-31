package com.chinmay.horobook.view.SongsListFragment

import android.media.AudioManager
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.chinmay.horobook.R
import com.chinmay.horobook.UrlConstants
import com.chinmay.horobook.model.SongsListData
import com.chinmay.horobook.util.getProgressDrawable
import com.chinmay.horobook.util.loadImage
import com.chinmay.horobook.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.item_song.view.*
import java.io.IOException


class SongsListAdapter(val songsList: ArrayList<SongsListData>) :
    RecyclerView.Adapter<SongsListAdapter.SongViewHolder>() {

    lateinit var listViewModel : ListViewModel

    fun updateSongsList(newSongsList: List<SongsListData>, viewModel: ListViewModel) {
        listViewModel = viewModel
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
            listViewModel.loadingSongs.value = true
            if (songsList[position].doNotPlaySong == false) {
                Navigation.findNavController(it)
                    .navigate(
                        SongsListFragmentDirections.actionPlayerFragment(
                            songsList[position].songUrl.toString(),
                            songsList[position].songImageUrl.toString(),
                            songsList[position].songName.toString()
                        )
                    )
                holder.view.list_clickable.isClickable = false
            } else {
                showDialog("Sorry!!", "You cannot play this song", holder)
            }

        }
        holder.view.imageView.loadImage(
            UrlConstants.media_url + songsList[position].songImageUrl,
            getProgressDrawable(holder.view.imageView.context)
        )
    }



    private fun showDialog(title: String, msg: String, holder: SongsListAdapter.SongViewHolder) {
        val builder = AlertDialog.Builder(holder.view.context)
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(msg)
        //builder.setIcon(android.R.drawable.ic_input_add)

        //performing positive action
        builder.setPositiveButton("OK") { dialogInterface, which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun playAudio(holder: SongViewHolder) {
        val url =
            "https://chandusanjith.pythonanywhere.com/media/mantra_image/Scam_1992_Theme_Official_-_Achint_1.mp3"
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            //Toast.makeText(holder.view.context, "Song is paused", Toast.LENGTH_SHORT).show()
        } else {

            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }

           // Toast.makeText(holder.view.context, "Song is playing", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getItemCount() = songsList.size

    class SongViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}