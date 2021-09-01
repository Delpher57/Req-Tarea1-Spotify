package com.example.tarea1spotifyr

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarea1spotifyr.Connectors.Information
import com.example.tarea1spotifyr.Connectors.SpotifyApiConnector
import com.example.tarea1spotifyr.UIManager.SongElement
import com.example.tarea1spotifyr.UIManager.SongElementAdapter
import kotlinx.android.synthetic.main.playlist_viewer.*
import kotlinx.android.synthetic.main.song_viewer.*

class SongActivity: AppCompatActivity() {

    private lateinit var songElementAdapter: SongElementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_viewer)


        songElementAdapter = SongElementAdapter(mutableListOf())
        recycler_song_list.adapter = songElementAdapter
        recycler_song_list.layoutManager = LinearLayoutManager(this)

        SpotifyApiConnector.createSpotifyAppRemote(this)
        updateTracks()

        pauseButton.setOnClickListener {
            SpotifyApiConnector.spotifyAppRemote?.playerApi?.pause()
        }

        resumeButton.setOnClickListener {
            SpotifyApiConnector.spotifyAppRemote?.playerApi?.resume()
        }

        add_button.setOnClickListener {
            songElementAdapter.deleteAllSongs()
            progressBarSong.visibility = View.VISIBLE

            val uri = "spotify:track:" + song_id_text.text
            Information.currentPlaylistId?.let { it1 -> SpotifyApiConnector.addSongToPlaylist(it1, uri) }
            song_id_text.text.clear()

            Handler().postDelayed({
                updateTracks()
            }, 3000)

        }




    }

    override fun onStop() {
        super.onStop()
        SpotifyApiConnector.deleteSpotifyAppRemote()
    }


    private fun updateTracks()  {
        songElementAdapter.deleteAllSongs()
        progressBarSong.visibility = View.VISIBLE

        var tracks = Information.tracks
        Information.currentPlaylistId?.let { SpotifyApiConnector.getPlaylistSongs(it) }


        Log.i("Iniciando wait loop", "---------------------------------------------------")
        Handler().postDelayed({
            tracks = Information.tracks
            Log.i("Actualizando interfaz", "---------------------------------------------------")
            progressBarSong.visibility = View.GONE
            if (tracks != null) {
                for (track in tracks!!) {
                    val newTr = track.track?.name?.let { track.track.uri?.let { it1 ->
                        SongElement(it,
                            it1
                        )
                    } }
                    if (newTr != null) {
                        songElementAdapter.addSong(newTr)
                    }
                }
            }
        }, 10000)
    }

}