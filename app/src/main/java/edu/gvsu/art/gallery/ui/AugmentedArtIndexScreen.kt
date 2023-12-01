package edu.gvsu.art.gallery.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.gvsu.art.client.Artist
import edu.gvsu.art.client.Artwork
import edu.gvsu.art.gallery.R
import edu.gvsu.art.gallery.TabScreen
import edu.gvsu.art.gallery.lib.Async
import edu.gvsu.art.gallery.navigateToArtworkDetail

@Composable
fun AugmentedArtIndexScreen(navController: NavController) {

    val artworks = when (val data = useFeaturedArtworks()) {
        is Async.Success -> data()
        else -> listOf()
    }

    val artist = when (val data = useArtist("637")) {
        is Async.Success -> data()
        else -> Artist()
    }

    val augmentedArtworksTemporal = listOf("3837", "3818", "26699", "12277", "7288", "17341", "2833", "3827", "10507", "3824", "3841")
    val artistWorks = artist.relatedWorks.filter { it.id in augmentedArtworksTemporal }
    print("Mathias artworks ${artistWorks}")
    val scrollState = rememberLazyListState()

    fun navigateToAugmentedArtwork(artwork: Artwork) {
        navController.navigateToArtworkDetail(
                TabScreen.Browse,
                artworkID = artwork.id
        )
    }

    Column {
        GalleryTopAppBar(
                title = stringResource(R.string.navigation_augmented_art_index),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
        )

        LazyColumn(state = scrollState) {
            item(key = "artist_name") {
                TitleText(
                        text = artist.name,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
            }
            items(artistWorks, key = { "artwork:${it.id}" }) { artwork ->
                Column {
                    ArtworkRow(
                            artwork = artwork,
                            modifier = Modifier
                                    .clickable { navigateToAugmentedArtwork(artwork) }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }

//        AugmentedArtLoadedView(
//                artworks = artworks,
//                onArtworkClick = { navigateToAugmentedArtwork(it)}
//        )


    }
}

@Composable
fun AugmentedArtLoadedView(
        artworks: List<Artwork>,
        onArtworkClick: (artwork: Artwork) -> Unit,
) {
    LazyColumn {
        itemsIndexed(artworks, key = {_, artwork -> artwork.id}) {index, artwork ->
            ArtworkRow(
                    artwork = artwork,
                    modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onArtworkClick(artwork) }
                            .padding(PaddingValues(horizontal = 16.dp, vertical = 8.dp))
            )
            if (index != artworks.lastIndex) {
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}
