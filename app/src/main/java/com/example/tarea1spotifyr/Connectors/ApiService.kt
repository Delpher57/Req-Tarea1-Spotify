package com.example.tarea1spotifyr.Connectors

import com.example.tarea1spotifyr.Objects.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.http.*


interface ApiService {

    @GET("me/playlists")
    fun getPlaylists(
        @Header("Authorization") Authorization: String
    ): Call<Pager<Playlist>>


    @GET("playlists/{playlist_id}/tracks")
    fun getPlaylistSongs(
        @Path("playlist_id") playlist_id: String,
        @Header("Authorization") Authorization: String,
        @Query("fields") fields : String = "items(track(name,uri))"
    ): Call<Pager<TrackGetter>>

    @POST("playlists/{playlist_id}/tracks")
    fun addSongToPlaylist(
        @Path("playlist_id") playlist_id: String,
        @Header("Authorization") Authorization: String,
        @Query("uris") uris : String
    ): Call<JsonResponse>

    @HTTP(method = "DELETE", path = "playlists/{playlist_id}/tracks", hasBody = true)
    fun deleteSongFromPlaylist(
        @Path("playlist_id") playlist_id: String,
        @Header("Authorization") Authorization: String,
        @Header("Content-Type") Content_Type : String = "application/json",
        @Body tracks: TracksDeleter
    ): Call<JsonResponse>


}