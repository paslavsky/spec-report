package com.github.paslavsky

import java.nio.file.Path
import java.util.*

object Configuration {
    var failed = "F"
    var passed = " "
    var filePattern = "{name}.spec"
    var specFolder: Path by LazyMutable.lazy {
        if (System.getProperties().any {
                it.key.toString().contains("gradle") || it.value.toString().contains("gradle")
            }) {
            Path.of(System.getProperty("user.dir"), "build", "reports", "specs")
        } else {
            Path.of(System.getProperty("user.dir"), "specs")
        }
    }

    init {
        Configuration.javaClass.getResourceAsStream("/spec-report.properties")?.use { input ->
            Properties().apply {
                load(input)
            }.let { props ->
                if ("failed" in props.keys) {
                    failed = props["failed"].toString()
                }
                if ("passed" in props.keys) {
                    passed = props["passed"].toString()
                }
                if ("filePattern" in props.keys) {
                    filePattern = props["filePattern"].toString()
                }
                if ("specFolder" in props.keys) {
                    specFolder = Path.of(props["specFolder"].toString())
                }
            }
        }
    }
}