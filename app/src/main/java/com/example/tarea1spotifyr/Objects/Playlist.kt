package com.example.tarea1spotifyr.Objects

import com.google.gson.annotations.SerializedName




/**
 * [Playlist object model](https://developer.spotify.com/web-api/object-model/#playlist-object-full).
 */
public class Playlist {
    @SerializedName("collaborative")
    var isCollaborative: Boolean? = null

    var description: String? = null
    @SerializedName("external_urls")
    var externalUrls: ExternalUrl? = null
    var href: String? = null
    var id: String? = null
    var images: List<Image>? = null
    var name: String? = null
    var owner: UserSimple? = null

    @SerializedName("public")
    var isPublic: Boolean? = null

    @SerializedName("snapshot_id")
    var snapshotId: String? = null

    @SerializedName("tracks")
    var tracksInfo: PlaylistTracksInformation? = null
    var type: String? = null
    var uri: String? = null

    override fun toString(): String {
        return "Simplified Playlist[$id]: $name"
    }
}