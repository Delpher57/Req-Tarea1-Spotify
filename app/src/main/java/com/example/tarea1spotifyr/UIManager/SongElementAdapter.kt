package com.example.tarea1spotifyr.UIManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea1spotifyr.Connectors.Information
import com.example.tarea1spotifyr.Connectors.SpotifyApiConnector
import com.example.tarea1spotifyr.R
import kotlinx.android.synthetic.main.song_element.view.*

class SongElementAdapter (
    private val songList: MutableList<SongElement>
) : RecyclerView.Adapter<SongElementAdapter.SongViewHolder>() {


    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.song_element,
                parent,
                false
            )
        )
    }

    fun addSong(song : SongElement) {
        songList.add(song)
        notifyItemInserted(songList.size -1)
    }

    fun deleteSong(song: SongElement) {
        songList.remove(song)
        notifyDataSetChanged()
    }

    fun deleteAllSongs() {
        songList.removeAll { true }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currentSong = songList[position]
        holder.itemView.apply {
            song_title_text.text = currentSong.title
            play_button.setOnClickListener { SpotifyApiConnector.playSong(currentSong.uri) }
            delete_song_button.setOnClickListener {
                Information.currentPlaylistId?.let { it1 ->
                    SpotifyApiConnector.deleteSongFromPlaylist(
                        it1, currentSong.uri)
                }
                deleteSong(currentSong)
            }
        }

    }

    override fun getItemCount(): Int {
        return songList.size
    }

}