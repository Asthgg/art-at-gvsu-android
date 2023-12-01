package edu.gvsu.art.client.common

import java.net.URL

fun listURL(string: String?): List<URL> {
    if (string.isNullOrBlank()) return emptyList()
    return string.split(";").map { URL(it) }
}