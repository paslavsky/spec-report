package com.github.paslavsky

import org.spekframework.spek2.lifecycle.*
import com.github.paslavsky.SpecNode.Companion.resolve
import java.io.BufferedWriter
import java.nio.file.Files
import kotlin.io.path.bufferedWriter

object SpecWriter : LifecycleListener {
    override fun afterExecuteGroup(group: GroupScope, result: ExecutionResult) = close(group, result)
    override fun afterExecuteTest(test: TestScope, result: ExecutionResult) = close(test, result)

    private fun close(scope: Scope, r: ExecutionResult) {
        val node = SpecNode.roots.resolve(scope).apply {
            result = r
        }
        if (node.parent == null) {
            try {
                Configuration.specFolder.let {
                    Files.createDirectories(it)
                    it.resolve(Configuration.filePattern.replace("{name}", node.name))
                }.bufferedWriter().use { out ->
                    node.children.forEach {
                        out.writeTree(it)
                    }
                }
            } catch (ignore: Exception) {
            }
        }
    }

    private fun BufferedWriter.writeTree(node: SpecNode, tab: String = "") {
        write(node, tab)
        for (child in node.children) {
            writeTree(child, "$tab  ")
        }
    }

    private fun BufferedWriter.write(node: SpecNode, tab: String) {
        val prefix = if (node.result == ExecutionResult.Success) Configuration.passed else Configuration.failed
        val empty by lazy { prefix.map { ' ' }.joinToString("") }
        node.name.split('\n').forEachIndexed { i, s ->
            write(if (i == 0) prefix else empty)
            write(tab)
            write(s)
            newLine()
        }
    }
}