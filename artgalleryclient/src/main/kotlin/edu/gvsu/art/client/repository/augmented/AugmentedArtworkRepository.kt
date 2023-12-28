package edu.gvsu.art.client.repository.augmented

import edu.gvsu.art.client.Artwork
import edu.gvsu.art.db.ArtGalleryDatabase

interface AugmentedArtworkRepository {
    fun isAugmented(artwork: Artwork): Boolean
//    fun all(): List<Artwork>
    // get entities
    // get sceneConfig
}


class DefaultAugmentedArtworkRepository(private val database: ArtGalleryDatabase) : AugmentedArtworkRepository {
    override fun isAugmented(artwork: Artwork): Boolean {


        return artwork.arDigitalAsset != null
    }
}
