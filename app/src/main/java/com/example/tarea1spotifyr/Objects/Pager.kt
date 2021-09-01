package com.example.tarea1spotifyr.Objects


/**
 * [Paging object model](https://developer.spotify.com/web-api/object-model/#paging-object)
 *
 * @param <T>
</T> */
class Pager<T> {
    var href: String? = null
    var items: List<T>? = null
    var limit = 0
    var next: String? = null
    var offset = 0
    var previous: String? = null
    var total = 0
}