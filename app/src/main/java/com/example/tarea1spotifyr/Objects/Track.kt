package com.example.tarea1spotifyr.Objects

import com.google.gson.annotations.SerializedName


/**
 * [Track object model](https://developer.spotify.com/web-api/object-model/#track-object-full)
 */
class Track {
    var href: String? = null
    var id: String? = null
    var name: String? = null

    var previewUrl: String? = null

    var type: String? = null
    var uri: String? = null

}