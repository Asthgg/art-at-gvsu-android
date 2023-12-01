package edu.gvsu.art.gallery.arartwork

interface AREntity {
    val type: ARAsset
    val name: String
//    val metadata:
    var hidden: Boolean

    fun hide(): Boolean
    fun load(): Boolean
    fun pause(): Boolean

}
