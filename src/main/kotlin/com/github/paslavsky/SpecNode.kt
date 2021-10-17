package com.github.paslavsky

import org.spekframework.spek2.lifecycle.ExecutionResult

data class SpecNode(val name: String, val parent: SpecNode? = null) {
    val children = mutableListOf<SpecNode>()
    var result: ExecutionResult = ExecutionResult.Failure(UndefinedResultError)

    companion object {
        val roots = mutableListOf<SpecNode>()

        fun MutableCollection<SpecNode>.resolve(path: String): SpecNode {
            val paths = path.split('/').iterator()
            var parent = paths.let {
                val next = paths.next()
                firstOrNull { it.name == next }.let {
                    it ?: SpecNode(next).also { newNode -> add(newNode) }
                }
            }
            var current = parent
            while (paths.hasNext()) {
                parent = current
                val next = paths.next()
                current = parent.children.firstOrNull { it.name == next }.let {
                    it ?: SpecNode(next, parent).also { newNode -> parent.children.add(newNode) }
                }
            }
            return current
        }
    }
}
