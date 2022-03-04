package com.chinmay.horobook.view.PlayerFragment

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chinmay.horobook.R
import com.chinmay.horobook.UrlConstants
import com.chinmay.horobook.util.getProgressDrawable
import com.chinmay.horobook.util.loadImage
import kotlinx.android.synthetic.main.fragment_player.*

class PlayerFragment : Fragment() {

    val args: PlayerFragmentArgs by navArgs()
    lateinit var handler: android.os.Handler
    private var mediaPlayer : MediaPlayer? = null
    private var songUrl : String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        songUrl = args.songUrl
        val songImageUrl = args.songImageUrl

        player_image.loadImage(
            UrlConstants.media_url + songImageUrl,
            getProgressDrawable(player_image.context)
        )
        playSong(songUrl!!)
        controlSound(songUrl!!)

    }

    private fun controlSound(songUrl: String) {

        fab_play.setOnClickListener{
            playSong(songUrl)
        }

        fab_pause.setOnClickListener{
            mediaPlayer?.pause()
            fab_play.isClickable = true
            fab_pause.isClickable = false
            fab_stop.isClickable = true
            Log.d("playerFragment", "Media Paused at : " + mediaPlayer!!.currentPosition/1000)
        }

        fab_stop.setOnClickListener{
            if (mediaPlayer!=null){
                fab_play.isClickable = true
                fab_pause.isClickable = false
                fab_stop.isClickable = false
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()

            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer!!.seekTo(progress)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }

    private fun playSong(songUrl: String) {
        fab_play.isClickable = false
        fab_pause.isClickable = true
        fab_stop.isClickable = true

        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context, Uri.parse(songUrl))

            initializeSeekBar(mediaPlayer!!)
        }
        mediaPlayer!!.start()
    }

    private fun initializeSeekBar(mediaPlayer: MediaPlayer) {
        seekbar.max = mediaPlayer.duration


        handler = android.os.Handler()
        handler.postDelayed(object : Runnable  {
            override fun run() {
                try {
                    seekbar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 1000)
                }catch (e: Exception){
                    //seekbar.progress = 0
                }
            }

        }, 0)
    }

    override fun onPause() {
        super.onPause()
        if(mediaPlayer!=null){
            //mediaPlayer!!.pause()
        }
        Log.d("playerFragment", "OnPause called")

    }

    override fun onStop() {
        super.onStop()
        if(mediaPlayer!=null){
            mediaPlayer!!.pause()
        }
        Log.d("playerFragment", "OnStop called")

    }

    override fun onStart() {
        super.onStart()
        playSong(songUrl!!)

    }

}