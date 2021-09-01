package com.example.tarea1spotifyr.Connectors

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.example.tarea1spotifyr.Connectors.ApiPlaylistReturn
import com.example.tarea1spotifyr.Objects.*
import com.spotify.protocol.types.Track
import kotlinx.android.synthetic.main.activity_splash.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotifyApiConnector {

    private val clientId = "6ecb1909988649768f2839eb68296f3a"
    private val redirectUri = "https://com.example.tarea1spotifyr/callback"
    var spotifyAppRemote: SpotifyAppRemote? = null




    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spotify.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)


    //creamos el appRemote
    fun createSpotifyAppRemote(actualContext: Context) {
        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(actualContext, connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("SpotifyAppRemote", "Connected! Yay!")
                // Now you can start interacting with App Remote
                //connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("SpotifyAppRemote", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

    //eliminamos el appRemote
    fun deleteSpotifyAppRemote() {
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }


    //obtenemos el tokenen el formato que el API pide
    private fun getTokenHeader(): String {
        return "Bearer ${TokenAuth.TOKEN}"
    }

    //obtenemos una lista de objetos playlist con las playlist del usuario
    fun getUserPlaylists() {
        var authToken = getTokenHeader()

        service.getPlaylists(authToken).enqueue(object : Callback<Pager<Playlist>> {
            override fun onResponse(
                call: Call<Pager<Playlist>>?, response: Response<Pager<Playlist>>?
            ) {
                val playlists = response?.body()
                Information.playlists = playlists?.items
                Log.i("PLAYLIST JSON", Gson().toJson(Information.playlists))
            }

            override fun onFailure(call: Call<Pager<Playlist>>, t: Throwable) {
                Log.i("ERROR PLAYLIST", " --- NO SE PUDO OBTENER LA PLAYLIST")
                t?.printStackTrace()
            }
        })
    }

    fun getPlaylistSongs(playlistId : String) {
        var authToken = getTokenHeader()

        service.getPlaylistSongs(playlistId, authToken).enqueue(object : Callback<Pager<TrackGetter>> {
            override fun onResponse(
                call: Call<Pager<TrackGetter>>?, response: Response<Pager<TrackGetter>>?
            ) {
                val tracks = response?.body()
                Information.tracks = tracks?.items
                Log.i("TRACKS JSON", Gson().toJson(Information.tracks))
            }

            override fun onFailure(call: Call<Pager<TrackGetter>>, t: Throwable) {
                Log.i("ERROR TRACKS", " --- NO SE PUDO OBTENER LAS TRACKS")
                t?.printStackTrace()
            }
        })
    }

    fun addSongToPlaylist(playlistId : String, songUri : String) {
        var authToken = getTokenHeader()

        service.addSongToPlaylist(playlistId, authToken, songUri).enqueue(object : Callback<JsonResponse> {
            override fun onResponse(
                call: Call<JsonResponse>?, response: Response<JsonResponse>?
            ) {
                val result = response?.body()
                Log.i("ADD SONG", Gson().toJson(result))
            }

            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                Log.i("ERROR add", " --- NO SE PUDO ANNADIR LAS TRACKS")
                t?.printStackTrace()
            }
        })
    }

    fun deleteSongFromPlaylist(playlistId : String, songUri : String)
    {
        var authToken = getTokenHeader()
        var tracksDeleter : TracksDeleter = TracksDeleter() //-------

        var deletingObject : TrackObjectDeleting = TrackObjectDeleting()
        deletingObject.uri = songUri

        var listTracks : List<TrackObjectDeleting> = listOf(deletingObject)
        tracksDeleter.tracks = listTracks

        service.deleteSongFromPlaylist(playlistId,authToken,"application/json",tracksDeleter).enqueue(object : Callback<JsonResponse> {
            override fun onResponse(
                call: Call<JsonResponse>?, response: Response<JsonResponse>?
            ) {
                val result = response?.body()
                Log.i("DELETE SONG", Gson().toJson(result))
            }

            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                Log.i("ERROR delete", " --- NO SE PUDO DELETE LAS TRACKS")
                t?.printStackTrace()
            }
        })
    }

    fun playSong(uri : String) {
        Log.d("Starting song:", uri)
        spotifyAppRemote?.let {
            // Play a song
            it.playerApi.play(uri)
            // Subscribe to PlayerState
            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)
            }
        }
    }

}