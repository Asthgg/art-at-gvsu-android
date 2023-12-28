package edu.gvsu.art.gallery.arartwork

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.gvsu.art.client.Artwork
import edu.gvsu.art.gallery.extensions.shareArtwork
import edu.gvsu.art.gallery.ui.TitleText
import edu.gvsu.art.gallery.ui.theme.Red

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun AugmentedSceneScreen(navController: NavController) {

    Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(
                text = "Prueba",
                modifier = Modifier
                        .fillMaxWidth(0.7f)
        )
        Row {

            IconButton(onClick = { Log.d("AUGMENTED",  "Prueba desde augmented scene") }) {
                Icon(imageVector = Icons.Default.ViewAgenda, contentDescription = null)
            }
        }
    }
}



