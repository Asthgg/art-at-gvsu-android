//package edu.gvsu.art.gallery.arartwork
//
//import androidx.camera.core.impl.Observable.Observer
//import dev.romainguy.kotlin.math.Mat4
//import io.github.sceneview.ar.ARSceneView
//
//enum class SceneType {
//    ARTWORK, SCULPTURE, DEFAULT
//}
//
//interface SceneObserver {
//    fun update(transform: Mat4)
//}
//
//interface Scene {
//    var type: SceneType
//    var arScene: ARSceneView
//    var nodes: MutableList<SceneObserver>
//
//    fun addObserver(observer: SceneObserver)
//
//    fun removeObserver(observer: SceneObserver)
//
//    fun notifyObserver()
//}
//
//class ARScene(override var type: SceneType, override var arScene: ARSceneView) : Scene {
//    override var nodes: MutableList<SceneObserver> = mutableListOf()
//
//
//    override fun addObserver(observer: SceneObserver) {
//        nodes.add(observer)
//    }
//
//    override fun removeObserver(observer: SceneObserver) {
//        nodes.remove(observer)
//    }
//
//    override fun notifyObserver() {
//        if (arScene.childNodes.isNotEmpty()) {
//            val transform = arScene.childNodes[0].transform
//            nodes.forEach { it.update(transform) }
//        }
//    }
//}
//
//class ARScene2 : Scene {
//    override var type: SceneType = SceneType.DEFAULT // Initialize with a default value
//    override var arScene: ARSceneView = ARSceneView()
//    private val nodes: MutableList<SceneObserver> = mutableListOf()
//
//    override fun configure() {
//        // Implementation logic for configure
//    }
//
//    override fun addObserver(observer: SceneObserver) {
//        nodes.add(observer)
//    }
//
//    override fun removeObserver(observer: SceneObserver) {
//        nodes.remove(observer)
//    }
//
//    override fun notifyObserver() {
//        if (arScene.childNodes.isNotEmpty()) {
//            val transform = arScene.childNodes[0].transform
//            nodes.forEach { it.update(transform) }
//        }
//    }
//}
//
//
//
//var a = ARScene()
//
//
