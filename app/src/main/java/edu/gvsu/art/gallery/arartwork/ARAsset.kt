package edu.gvsu.art.gallery.arartwork

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.net.URI

enum class ARAsset: DownloadableAsset {
    IMAGE {
        fun <T> download(uri: URI, processor: (FileInputStream) -> T): T? {
            print("GETTING RESOURCE: $uri")
            val resourceFile = File(uri)

            try {
                FileInputStream(resourceFile).use { stream ->
                    return processor(stream)
                }
            } catch (e: IOException) {
                println("Error reading file: $e")
            }
            return null
        }

        override val downloaded: Boolean
            get() = TODO("Not yet implemented")
        override val retries: Int
            get() = TODO("Not yet implemented")
        override val localUrl: URI
            get() = TODO("Not yet implemented")
        override val remoteUrl: URI
            get() = TODO("Not yet implemented")

        override fun <T> download(): T {
            TODO("Not yet implemented")
        }
    },
//    VIDEO, MESH,
}

