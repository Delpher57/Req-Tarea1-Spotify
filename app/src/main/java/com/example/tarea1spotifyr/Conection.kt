package com.example.tarea1spotifyr

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

class Conection {
    private val clientId = "6ecb1909988649768f2839eb68296f3a"
    private val redirectUri = "http://com.example.tarea1spotifyr/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null
}