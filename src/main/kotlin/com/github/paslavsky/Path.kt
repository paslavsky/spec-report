package com.github.paslavsky

import org.spekframework.spek2.lifecycle.Scope
import org.spekframework.spek2.runtime.scope.Path
import kotlin.reflect.full.memberProperties

val Scope.path get() = this::class.memberProperties.first { it.name == "path" }.call(this) as Path
