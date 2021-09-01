package com.example.tarea1spotifyr.Connectors

import com.example.tarea1spotifyr.Objects.Pager
import com.example.tarea1spotifyr.Objects.Playlist
import com.example.tarea1spotifyr.Objects.SimpleTrack
import com.example.tarea1spotifyr.Objects.TrackGetter

object Information {
    var playlists: List<Playlist>? = null
    var tracks: List<TrackGetter>? = null
    var currentPlaylistUri : String? = null
    var currentPlaylistId : String? = null
}
