package com.example.music_pleer

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterSongs(
    private val songs: Array<Int>,
    private val context: Context
) : RecyclerView.Adapter<AdapterSongs.ViewHolder> () {
    private var currentSongsPosition: Int = -1
    private var mediaPlayer: MediaPlayer? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val name: TextView = itemView.findViewById(R.id.name)
        val artist: TextView = itemView.findViewById(R.id.artist)
//        val playListBtn: ImageButton = itemView.findViewById(R.id.play_list_btn)
        val context: Context = itemView.context

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            //blyat what is doesnt work T__T
            //I need pause kurva
            //Foget adout my emotional explosion =/
            
                if (currentSongsPosition == position && mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                } else {
                    playSong(position)
                }

        }

        private fun playSong(position: Int) {
            if (currentSongsPosition != -1 && mediaPlayer?.isPlaying == true) {
                mediaPlayer?.stop()
            }
            currentSongsPosition = position
            mediaPlayer = MediaPlayer.create(context, songs[position])
            mediaPlayer?.start()
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //information about compozition
        val currentSong = songs[position]

        holder.name.text = holder.context.resources.getResourceEntryName(currentSong)
        holder.artist.text = currentSong.toString()
    }

    override fun getItemCount(): Int {
        return songs.size
    }

}