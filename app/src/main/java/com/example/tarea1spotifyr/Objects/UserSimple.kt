package com.example.tarea1spotifyr.Objects

import com.google.gson.annotations.SerializedName


class UserSimple {
    var href: String? = null
    var id: String? = null
    var type: String? = null
    var uri: String? = null

    @SerializedName("external_urls")
    var externalUrls: ExternalUrl? = null

    @SerializedName("display_name")
    var displayName: String? = null
    override fun toString(): String {
        return "User[$id]"
    }
}