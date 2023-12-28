package edu.gvsu.art.gallery.arartwork

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.Session
import com.google.ar.core.TrackingFailureReason
import edu.gvsu.art.client.Artwork
import edu.gvsu.art.gallery.R
import edu.gvsu.art.gallery.ui.TitleText
import edu.gvsu.art.gallery.ui.theme.ArtAtGVSUTheme
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView
import kotlinx.parcelize.Parcelize
import java.net.URL

private const val INTENT_AUGMENTED_ARTWORK = "augmented_artwork"

fun augmentedIntent(user: AugmentedArtworkConfiguration, context: Context): Intent {
    return Intent(context, AugmentedArtworkActivity::class.java).apply {
        putExtra(INTENT_AUGMENTED_ARTWORK, user)
    }

}

class AugmentedArtworkActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val m = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(INTENT_AUGMENTED_ARTWORK, AugmentedArtworkConfiguration::class.java)
        } else {
            intent.getParcelableExtra(INTENT_AUGMENTED_ARTWORK)
        }

        Log.d("AR", m.toString())


        setContent {
            if (m != null) {
                AugmentedArtwork(m)
            }
        }
    }
}

@Composable
fun AugmentedArtwork(m: AugmentedArtworkConfiguration) {
    ArtAtGVSUTheme {
        MainStackNavigationView()
    }
}



enum class SceneType(val artwork: String) {
    ARTWORK("artwork") {
        fun getConfig(session: Session): Config {
            val config = Config(session)
            val imageDatabase = AugmentedImageDatabase(session)
            config.augmentedImageDatabase = imageDatabase

            return config
        }
    }
}


fun buildConfiguration(artwork: Artwork): AugmentedArtworkConfiguration {
    val type = artwork.arType

    val config = SceneType.ARTWORK

    val entities = artwork.ar3DFiles

    return AugmentedArtworkConfiguration(type, null, entities)
}

@Parcelize
data class AugmentedArtworkConfiguration(
        val type: String = "Artwork",
        val reference: URL?,
        val entities: List<URL> = listOf<URL>(),
) : Parcelable

@Composable
fun MainStackNavigationView() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

//    Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//    ) {
//        TitleText(
//                text = "Prueba ACTIVITY",
//                modifier = Modifier
//                        .fillMaxWidth(0.7f)
//        )
//        Row {
//
//            IconButton(onClick = { Log.d("AUGMENTED",  "Prueba desde augmented scene") }) {
//                Icon(imageVector = Icons.Default.ViewAgenda, contentDescription = null)
//            }
//        }
//    }
    // A surface container using the 'background' color from the theme


    Box(
            modifier = Modifier.fillMaxWidth(),
    ) {
        // The destroy calls are automatically made when their disposable effect leaves
        // the composition or its key changes.
        val engine = rememberEngine()
        val modelLoader = rememberModelLoader(engine)
        val materialLoader = rememberMaterialLoader(engine)
        val cameraNode = rememberARCameraNode(engine)
        val childNodes = rememberNodes()
        val view = rememberView(engine)
        val collisionSystem = rememberCollisionSystem(view)

        var planeRenderer by remember { mutableStateOf(true) }

        val modelInstances = remember { mutableListOf<ModelInstance>() }
        var trackingFailureReason by remember {
            mutableStateOf<TrackingFailureReason?>(null)
        }
        var frame by remember { mutableStateOf<Frame?>(null) }
        ARScene(
                modifier = Modifier.fillMaxSize(),
                childNodes = childNodes,
                engine = engine,
                view = view,
                modelLoader = modelLoader,
                collisionSystem = collisionSystem,
                sessionConfiguration = { session, config ->
                    config.depthMode =
                            when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                                true -> Config.DepthMode.AUTOMATIC
                                else -> Config.DepthMode.DISABLED
                            }
                    config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
                    config.lightEstimationMode =
                            Config.LightEstimationMode.ENVIRONMENTAL_HDR
                },
                cameraNode = cameraNode,
                planeRenderer = planeRenderer,
                onTrackingFailureChanged = {
                    trackingFailureReason = it
                },
                onSessionUpdated = { session, updatedFrame ->
                    frame = updatedFrame

                    if (childNodes.isEmpty()) {
                        updatedFrame.getUpdatedPlanes()
                                .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                                ?.let { it.createAnchorOrNull(it.centerPose) }?.let { anchor ->
                                    childNodes += createAnchorNode(
                                            engine = engine,
                                            modelLoader = modelLoader,
                                            materialLoader = materialLoader,
                                            modelInstances = modelInstances,
                                            anchor = anchor
                                    )
                                }
                    }
                },
                onGestureListener = rememberOnGestureListener(
                        onSingleTapConfirmed = { motionEvent, node ->
                            if (node == null) {
                                val hitResults = frame?.hitTest(motionEvent.x, motionEvent.y)
                                hitResults?.firstOrNull {
                                    it.isValid(
                                            depthPoint = false,
                                            point = false
                                    )
                                }?.createAnchorOrNull()
                                        ?.let { anchor ->
                                            planeRenderer = false
                                            childNodes += createAnchorNode(
                                                    engine = engine,
                                                    modelLoader = modelLoader,
                                                    materialLoader = materialLoader,
                                                    modelInstances = modelInstances,
                                                    anchor = anchor
                                            )
                                        }
                            }
                        })
        )
        Text(
                modifier = Modifier
                        .systemBarsPadding()
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                color = Color.White,
                text = trackingFailureReason?.let {
                    it.getDescription(LocalContext.current)
                } ?: if (childNodes.isEmpty()) {
                    stringResource(R.string.home_augmented_art_index)
                } else {
                    stringResource(R.string.navigation_augmented_art_index)
                }
        )
    }
}


fun createAnchorNode(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        modelInstances: MutableList<ModelInstance>,
        anchor: Anchor
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
    val modelNode = ModelNode(
            modelInstance = modelInstances.apply {
                if (isEmpty()) {

                    val kModelFile = "models/school_of_fish.glb"
                    val kMaxModelInstances = 10

                    this += modelLoader.createInstancedModel(kModelFile, kMaxModelInstances)
                }
            }.removeLast(),
            // Scale to fit in a 0.5 meters cube
            scaleToUnits = 0.5f
    ).apply {
        // Model Node needs to be editable for independent rotation from the anchor rotation
        isEditable = true
    }
    val boundingBoxNode = CubeNode(
            engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White.copy(alpha = 0.5f))
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(boundingBoxNode)
    anchorNode.addChildNode(modelNode)

    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = { editingTransforms ->
            boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
        }
    }
    return anchorNode
}


