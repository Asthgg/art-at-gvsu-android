package edu.gvsu.art.gallery.arartwork

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.gvsu.art.client.Artwork
import edu.gvsu.art.gallery.R
import edu.gvsu.art.gallery.TabScreen
import edu.gvsu.art.gallery.ui.ArtistDetailScreen

object Route {
    const val AugmentedArtworkScene = "browse/artworks/{artwork_id}/augmented"
    const val BrowseAugmentedArtworkIndex = "browse/artworks"
}




//fun NavController.navigateToAugmentedArtwork(tabScreen: TabScreen, artistID: String) =
//        navigate("browse/artworks/${artistID}/augmented")


//@OptIn(ExperimentalComposeUiApi::class, ExperimentalPagerApi::class)
//fun NavGraphBuilder.augmentedArtworkScene(route: String, navController: NavController) {
//    composable(route) { backStackEntry ->
//        AugmentedSceneScreen(
//                navController
//        )
//    }
//}
