package com.github.paslavsky

import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root

abstract class Spec(body: Root.() -> Unit) : Spek({
    registerListener(SpecWriter)
    body(this)
})