package org.chrishatton.multimvp.util

import javafx.scene.Parent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.chrishatton.multimvp.ui.BaseFxmlView
import java.io.InputStream
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <T: Parent> fxid(id: String? = null) = object : ReadOnlyProperty<BaseFxmlView<*, *>, T> {
    override fun getValue(thisRef: BaseFxmlView<*, *>, property: KProperty<*>): T {
        val effectiveId = id ?: property.name
        val value = thisRef.fxmlLoader.namespace[effectiveId] ?: throw Exception("fx:id $effectiveId in ${thisRef.fxmlLoader.location}")
        return value as? T ?: throw Exception("fx:id $effectiveId in ${thisRef.fxmlLoader.location}")
    }
}

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
fun <T:Parent> fxml(name: String? = null) = object : ReadOnlyProperty<BaseFxmlView<*,*>, T> {

    private var parent : T? = null

    override fun getValue(thisRef: BaseFxmlView<*, *>, property: KProperty<*>): T = parent ?: run {
        val effectiveName = (name ?: thisRef.javaClass.simpleName) + ".fxml"
        val resourceStream : InputStream = thisRef::class.java.getResourceAsStream(effectiveName)
        thisRef.fxmlLoader.load<T>(resourceStream)
    }.also { parent = it }
}
