//package edu.gvsu.art.gallery.arartwork
//
//import edu.gvsu.art.client.Artwork
//
//enum class Reference {
//    IMAGE, OBJECT
//}
//
//interface SceneConfig {
//    var type: SceneType
//    var withTitle: Boolean
//    var withDescription: Boolean
//    var withButtonControls: Boolean
//}
//
//interface Configuration {
//    var entities: List<Entity>
//    var reference: Reference
//    var sceneConfig: SceneConfig
//}
//
//interface Controller {
//    var configuration: Configuration
//
//    fun configure()
//    fun startTracking()
//    fun stopTracking()
//}
//
//class SceneController(override var configuration: Configuration) : Controller {
//    private lateinit var scene: Scene
//
//
//    override fun configure() {
//        configuration.entities.forEach { entity ->
//            scene.addObserver(entity)
//        }
//    }
//
//    override fun startTracking() {
//        TODO("Not yet implemented")
//    }
//
//    override fun stopTracking() {
//        TODO("Not yet implemented")
//    }
//
//}
//
//
//fun getSceneController(artwork: Artwork) {
//    // call parser
//    var s = SceneController()
//
//    //
////    var image = artwork.mediaLarge?.let { AREntity(coordinates = artwork.arCoordinates, name = "Image", type = ARAsset.IMAGE, url = it) }
////    var video = artwork.arDigitalAsset?.let { AREntity(coordinates = artwork.arCoordinates, name = "Video", type = ARAsset.IMAGE, url = it) }
////    var objects = AREntity(coordinates = artwork.arCoordinates, name = "Objects", type = ARAsset.IMAGE, url = artwork.ar3DFiles)
//
//}