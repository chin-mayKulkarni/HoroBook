package com.chinmay.horobook.view.PlayerFragment

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
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
import java.util.concurrent.TimeUnit


class PlayerFragment : Fragment() {

    val args: PlayerFragmentArgs by navArgs()
    lateinit var handler: android.os.Handler
    lateinit var seekHandler: android.os.Handler
    private var mediaPlayer: MediaPlayer? = null
    private var songUrl: String? = null
    private var play = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekHandler = Handler()



        songUrl = UrlConstants.media_url + args.songUrl
        val songImageUrl = args.songImageUrl
        Log.d("playerFragment", "SongListData value is : " + args.songName)
        val songName = args.songName

        song_name.text = songName
        song_name.isSelected = true

        player_btn.setImageResource(R.drawable.ic_pause)
        player_image.loadImage(
            UrlConstants.media_url + songImageUrl,
            getProgressDrawable(player_image.context)
        )
        playSong(songUrl!!)
        controlSound(songUrl!!)

    }

    private fun controlSound(songUrl: String) {

        player_btn.setOnClickListener {

            if (play) {
                mediaPlayer?.pause()
                Log.d("playerFragment", "Media Paused at : " + mediaPlayer!!.currentPosition / 1000)
            } else {
                playSong(songUrl)

                Log.d(
                    "playerFragment",
                    "Media is playing : " + mediaPlayer!!.currentPosition / 1000
                )
            }
            play = !play
            player_btn.setImageResource(if (play) R.drawable.ic_pause else R.drawable.ic_play)
        }


        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })

    }

    private fun playSong(songUrl: String) {

        Log.d("Player fragment", "Song Url :$songUrl")


        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(songUrl))


            initializeSeekBar(mediaPlayer!!)
        }
        seekHandler.postDelayed(updateSeekBar, 15)
        mediaPlayer!!.start()
        Log.d("playerFragment", "Total time : " + mediaPlayer!!.duration)
        Log.d("playerFragment", "current time : " + mediaPlayer!!.currentPosition)
        var millis = mediaPlayer!!.duration.toLong()

        //val song_time_sec = mediaPlayer!!.duration / 1000 % 60
        val hms = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(millis),
            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        )
        Log.d("playerFragment", "Total time in minutes : " + hms)
    }


    private fun initializeSeekBar(mediaPlayer: MediaPlayer) {
        seekbar.max = mediaPlayer.duration


        handler = android.os.Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    seekbar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 15)
                } catch (e: Exception) {
                    //seekbar.progress = 0
                }
            }
        }, 0)
    }

    private val updateSeekBar: Runnable = object : Runnable {
        override fun run() {
            val totalDuration = mediaPlayer!!.duration.toLong()
            val currentDuration = mediaPlayer!!.currentPosition.toLong()

            if (mediaPlayer!!.isPlaying) {
                // Displaying Total Duration time
                song_end.setText("" + milliSecondsToTimer(totalDuration - currentDuration))
                // Displaying time completed playing
                song_start.setText("" + milliSecondsToTimer(currentDuration))
                // Call this thread again after 15 milliseconds => ~ 1000/60fps
                seekHandler.postDelayed(this, 15)
            }
        }
    }

    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""

        // Convert total duration into time
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        // Add hours if there
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        // Prepending 0 to seconds if it is one digit
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutes:$secondsString"

        // return timer string
        return finalTimerString
    }


    override fun onPause() {
        super.onPause()
        if (mediaPlayer != null) {
            //mediaPlayer!!.pause()
        }
        Log.d("playerFragment", "OnPause called")

    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer != null) {
            mediaPlayer!!.pause()
        }
        Log.d("playerFragment", "OnStop called")

    }

    override fun onStart() {
        super.onStart()
        playSong(songUrl!!)

    }

}

