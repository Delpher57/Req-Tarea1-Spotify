package com.example.tarea1spotifyr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;



class MainActivity : AppCompatActivity() {

    private val clientId = "6ecb1909988649768f2839eb68296f3a"
    private val redirectUri = "http://com.example.tarea1spotifyr/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        val connectionParams = ConnectionParams.Builder(clientId)
                .setRedirectUri(redirectUri)
                .showAuthView(true)
                .build()


        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    private fun connected() {
        spotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL")
        spotifyAppRemote?.playerApi?.subscribeToPlayerState()?.setEventCallback {
            val track: Track = it.track
            Log.d("MainActivity", track.name + " by " + track.artist.name)
        }
    }

    override fun onStop() {
        super.onStop()
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }
}