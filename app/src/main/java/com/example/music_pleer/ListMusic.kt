package com.example.music_pleer

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListMusic : AppCompatActivity() {
    private val songs = arrayOf(
        R.raw.lo_fi1,
        R.raw.lofi_music,
        R.raw.backgroundmusic,
        R.raw.dontstopmeabstractfuturebass162753,
        R.raw.lofichill,
        R.raw.lostambientlofi60s,
        R.raw.onceinparis,
        R.raw.sleepycat,
        R.raw.tvaritokyocafe
    )

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var playListBtn: ImageButton
    private var isSongPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.music_list)

        val backgroundImageView = findViewById<ImageView>(R.id.backgrund)
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.wanella)
        val blurredBitmap = Blur.blurBitmap(this, originalBitmap)
        Glide.with(this).load(blurredBitmap).into(backgroundImageView)

        mediaPlayer = MediaPlayer()
        val songRecyclerView = findViewById<RecyclerView>(R.id.song_list)
        songRecyclerView.layoutManager = LinearLayoutManager(this)
        val songAdapter = AdapterSongs(songs, this)
        songRecyclerView.adapter = songAdapter
        songRecyclerView.isNestedScrollingEnabled = false

        val next_page = findViewById<ImageButton>(R.id.next_page)
        next_page.setOnClickListener {
            val intent = Intent(this, Song::class.java)
            startActivity(intent)
        }

        mediaPlayer = MediaPlayer()
        for (song in songs) {
            mediaPlayer = MediaPlayer.create(applicationContext, song)
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.stop()
                mediaPlayer.prepareAsync()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
