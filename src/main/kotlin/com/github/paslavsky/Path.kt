package com.github.paslavsky

import org.spekframework.spek2.lifecycle.Scope
import kotlin.reflect.full.memberProperties

val Scope.path: String
    get() {
        val root = findRoot(this)
        val rootPath = root.reflectGetPath().replace('/', '.')
        if (root === this) {
            return rootPath
        }
        val path = reflectGetPath()
        return rootPath + path.substring(rootPath.length)
    }

private fun Scope.reflectGetPath() = this::class.memberProperties.first { it.name == "path" }.call(this).toString()

private tailrec fun findRoot(scope: Scope): Scope {
    if (scope.parent == null)
        return scope
    return findRoot(scope.parent!!)
}