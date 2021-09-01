package com.example.tarea1spotifyr.UIManager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea1spotifyr.Connectors.Information
import com.example.tarea1spotifyr.Connectors.SpotifyApiConnector
import com.example.tarea1spotifyr.MainActivity
import com.example.tarea1spotifyr.PlaylistActivity
import com.example.tarea1spotifyr.R
import com.example.tarea1spotifyr.SongActivity
import kotlinx.android.synthetic.main.playlist_element.view.*


class PlaylistElementAdapter (
    private val playlistList: MutableList<PlaylistElement>,
    private val actualContext: Context
) : RecyclerView.Adapter<PlaylistElementAdapter.PlaylistViewHolder>() {





    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.playlist_element,
                parent,
                false
            )
        )
    }

    fun addPlaylist(playlist : PlaylistElement) {
        playlistList.add(playlist)
        notifyItemInserted(playlistList.size -1)

    }

    fun deletePlaylist(playlist: PlaylistElement) {
        playlistList.remove(playlist)
        notifyDataSetChanged()
    }

    fun deleteAllPlaylists() {
        playlistList.removeAll { true }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val currentPlaylist = playlistList[position]

        holder.itemView.apply {
            playlist_title_text.text = currentPlaylist.title
            playlist_play_button.setOnClickListener {
                Information.currentPlaylistUri = currentPlaylist.uri
                Information.currentPlaylistId = currentPlaylist.id
                val changePage = Intent(actualContext, SongActivity::class.java)
                actualContext.startActivity(changePage)
            } //annadimos un conector al boton
        }
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

}