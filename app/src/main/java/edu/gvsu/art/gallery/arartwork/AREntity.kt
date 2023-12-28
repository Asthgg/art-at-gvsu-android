//package edu.gvsu.art.gallery.arartwork
//
//import dev.romainguy.kotlin.math.Mat4
//import io.github.sceneview.math.Position
//import io.github.sceneview.node.ModelNode
//import java.net.URL
//
//interface Entity: SceneObserver {
//    val type: ARAsset
//    val name: String
//    var url: URL
//    var coordinates: List<Double>
//    val node: ModelNode
//
//    fun createEntity()
//
//
//    abstract fun ModelNode(modelInstance: URL, scaleToUnits: Float, centerOrigin: Position?): ModelNode
//
//
//}
//
//class AREntity(
//        override val type: ARAsset,
//        override val name: String,
//        override var url: URL,
//        override var coordinates: List<Double>,
//        override var node: ModelNode
//) : Entity {
////    constructor(coordinates: List<Double>, name: String, type: ARAsset, url: URL)
////
////    override fun createEntity() {
////        node = ModelNode(
////                modelInstance = url,
////                // Scale to fit in a 0.5 meters cube
////                scaleToUnits = 0.5f,
////                // Place using the coordinates
////                coordinates
////        ).apply {
////            // Make the node editable for rotation and scale
////            isEditable = true
////        }
////    }
//
//    override fun update(transform: Mat4) {
//        node.transform = transform
//    }
//
//    override fun createEntity() {
//        TODO("Not yet implemented")
//    }
//
//    override fun ModelNode(modelInstance: URL, scaleToUnits: Float, centerOrigin: Position?): ModelNode {
//        TODO("Not yet implemented")
//    }
//
//    override fun update() {
//        TODO("Not yet implemented")
//    }
//
//
//
//}
