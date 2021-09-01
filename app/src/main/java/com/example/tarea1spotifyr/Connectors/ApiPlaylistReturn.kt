package com.example.tarea1spotifyr.Connectors

import com.example.tarea1spotifyr.Objects.Playlist

interface ApiPlaylistReturn {
    fun onSuccess(value : Result<List<Playlist>?>);
    fun onFailure()
}