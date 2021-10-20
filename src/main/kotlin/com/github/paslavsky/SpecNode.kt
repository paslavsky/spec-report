package com.github.paslavsky

import org.spekframework.spek2.lifecycle.ExecutionResult
import org.spekframework.spek2.lifecycle.Scope

data class SpecNode(val name: String, val parent: SpecNode? = null) {
    val children = mutableListOf<SpecNode>()
    var result: ExecutionResult = ExecutionResult.Failure(UndefinedResultError)

    companion object {
        val roots = mutableListOf<SpecNode>()

        fun MutableCollection<SpecNode>.resolve(scope: Scope): SpecNode {
            var collection = this
            var parent: SpecNode? = null
            return sequence {
                var tmp = scope
                yield(tmp)
                while (tmp.parent != null) {
                    tmp = tmp.parent!!
                    yield(tmp)
                }
            }.toList().reversed().mapIndexed { index, it ->
                if (index == 0)
                    it.path.toString().replace('/', '.')
                else
                    it.path.name
            }.map { name ->
                (collection.firstOrNull { it.name == name } ?: SpecNode(name, parent).apply {
                    collection.add(this)
                }).also { node ->
                    collection = node.children
                    parent = node
                }
            }.last()
        }
    }
}
