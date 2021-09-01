package com.example.tarea1spotifyr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.example.tarea1spotifyr.Connectors.Information
import com.example.tarea1spotifyr.Connectors.SpotifyApiConnector
import com.example.tarea1spotifyr.Objects.Pager
import com.example.tarea1spotifyr.Objects.Playlist
import com.example.tarea1spotifyr.UIManager.PlaylistElement
import com.example.tarea1spotifyr.UIManager.PlaylistElementAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.playlist_viewer.*
import retrofit2.Call
import retrofit2.Callback

class PlaylistActivity: AppCompatActivity() {

    private lateinit var playlistElementAdapter: PlaylistElementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playlist_viewer)


        playlistElementAdapter = PlaylistElementAdapter(mutableListOf(), this)
        recycler_playlist_list.adapter = playlistElementAdapter
        recycler_playlist_list.layoutManager = LinearLayoutManager(this)

        updatePlaylists()


    }


    fun updatePlaylists()  {
        var playlists = Information.playlists
        SpotifyApiConnector.getUserPlaylists()


        Log.i("Iniciando wait loop", "---------------------------------------------------")
        Handler().postDelayed({
            playlists = Information.playlists
            Log.i("Actualizando interfaz", "---------------------------------------------------")
            progressBarPlaylist.visibility = View.GONE
            if (playlists != null) {
                for (playlist in playlists!!) {
                    val newPl = playlist.name?.let { playlist.uri?.let { it1 ->
                        playlist.id?.let { it2 ->
                            PlaylistElement(it,
                                it1, it2
                            )
                        }
                    } }
                    if (newPl != null) {
                        playlistElementAdapter.addPlaylist(newPl)
                    }
                }
            }
        }, 10000)
    }



}