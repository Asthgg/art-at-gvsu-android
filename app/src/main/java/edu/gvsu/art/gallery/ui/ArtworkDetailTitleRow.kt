package edu.gvsu.art.gallery.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import edu.gvsu.art.client.Artwork
import edu.gvsu.art.gallery.Route
import edu.gvsu.art.gallery.extensions.shareArtwork
import edu.gvsu.art.gallery.ui.theme.ArtAtGVSUTheme
import edu.gvsu.art.gallery.ui.theme.Red

@Composable
fun ArtworkDetailTitleRow(
    artwork: Artwork,
    toggleFavorite: () -> Unit = {},
    isFavorite: Boolean = false,
    isAugmented: Boolean = false,
    navigateToAugmentedArtwork: () -> Unit = {}
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
            text = artwork.name,
            modifier = Modifier
                .fillMaxWidth(0.7f)
        )
        Row {
            isAugmented.let {
                IconButton(onClick = {
                    Log.d("AUGMENTED", "artwork button clicked! ${artwork.arDigitalAsset}")
                    navigateToAugmentedArtwork()
                 }) {
                    Icon(Icons.Default.ViewInAr, contentDescription = null)
                }
            }

            IconButton(onClick = { context.shareArtwork(artwork) }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(
                onClick = { toggleFavorite() }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = Red,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewArtworkDetailTitleRow() {
    val artwork = Artwork(name = "My Artwork")
    ArtAtGVSUTheme {
        ArtworkDetailTitleRow(artwork = artwork)
    }
}


@Composable
@Preview("Long title that could wrap")
fun PreviewArtworkDetailTitleRowLongText() {
    val artwork = Artwork(name = "My Artwork Continuous Stream of Words")
    ArtAtGVSUTheme {
        ArtworkDetailTitleRow(artwork = artwork)
    }

}
