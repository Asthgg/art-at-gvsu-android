package edu.gvsu.art.gallery.arartwork

import java.net.URI

interface DownloadableAsset {
    val downloaded: Boolean
    val retries: Int
    val localUrl: URI
    val remoteUrl: URI

    fun <T> download(): T


}