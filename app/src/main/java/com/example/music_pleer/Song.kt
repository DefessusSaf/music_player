package com.example.music_pleer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class Song : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var runnable : Runnable
    private var handler = Handler()
    private val songs = arrayOf(R.raw.lo_fi1, R.raw.lofi_music)
    private var currentSongIndex = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.lo_fi1)


        val backgroundImageView = findViewById<ImageView>(R.id.backgrund)
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.wanella)
        val blurredBitmap = Blur.blurBitmap(this, originalBitmap)
        Glide.with(this).load(blurredBitmap).into(backgroundImageView)
        
        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        seekbar.progress = 0
        seekbar.max = mediaPlayer.duration

        val back_page = findViewById<ImageButton>(R.id.back_page)
        back_page.setOnClickListener {
            val intent = Intent(this, ListMusic::class.java)
            startActivity(intent)


        }

        val play_btn = findViewById<ImageButton>(R.id.play_btn)
        play_btn.setOnClickListener {
            if(!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                play_btn.setImageResource(R.drawable.baseline_pause_24)
            }
            else {
                mediaPlayer.pause()
                play_btn.setImageResource(R.drawable.baseline_play_arrow_24)

            }
        }

        val next_btn = findViewById<ImageButton>(R.id.next_btn)
        next_btn.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
            currentSongIndex ++

            if (currentSongIndex >= songs.size) {
                currentSongIndex  = 0
            }
            mediaPlayer = MediaPlayer.create(applicationContext, songs[currentSongIndex])
            mediaPlayer.start()
        }

        val back_btn = findViewById<ImageButton>(R.id.back_btn)
        back_btn.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.reset()
            currentSongIndex --

            if (currentSongIndex < 0) {
                currentSongIndex  = songs.size - 1
            }
            mediaPlayer = MediaPlayer.create(applicationContext, songs[currentSongIndex])
            mediaPlayer.start()
        }



        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if(changed) {
                    mediaPlayer.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        runnable = Runnable{
            if (mediaPlayer.isPlaying && ! isFinishing) {
                seekbar.progress = mediaPlayer.currentPosition
            }
            handler.postDelayed(runnable, 1000)
        }

        handler.postDelayed(runnable, 1000)
        mediaPlayer.setOnCompletionListener {
            play_btn.setImageResource(R.drawable.baseline_play_arrow_24)
            if (!isFinishing) {
                seekbar.progress = 0
                currentSongIndex++
                if (currentSongIndex >= songs.size) {
                    currentSongIndex = 0
                }
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer = MediaPlayer.create(applicationContext, songs[currentSongIndex])
                mediaPlayer.start()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        mediaPlayer.release()
    }
}