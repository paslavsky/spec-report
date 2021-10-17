package com.github.paslavsky

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LazyMutable<T>(val initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private object NoValue
    private var prop: Any? = NoValue

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (prop == NoValue) {
            synchronized(this) {
                return if (prop == NoValue) initializer().also { prop = it } else prop as T
            }
        } else prop as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        synchronized(this) {
            prop = value
        }
    }

    companion object {
        fun <T> lazy(initializer: () -> T): ReadWriteProperty<Any?, T> = LazyMutable(initializer)
    }
}