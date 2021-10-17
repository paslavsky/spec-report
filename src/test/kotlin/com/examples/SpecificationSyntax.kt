package com.examples

import com.github.paslavsky.Spec
import org.spekframework.spek2.style.specification.describe
import kotlin.test.*

/**
 * Please see `com.examples.SpecificationSyntax.spec` file at `examples` folder.
 */
object SpecificationSyntax : Spec({
    describe("Set") {
        val set by memoized { mutableSetOf<String>() }

        describe("adding an item") {
            beforeEachTest {
                set.add("foo")
            }

            it("should have a size of 1") {
                assertEquals(1, set.size)
            }

            it("should contain foo") {
                assertTrue(set.contains("foo"))
            }
        }

        describe("empty set") {
            it("should have a size of 0") {
                assertEquals(0, set.size)
            }

            it("should throw when first is invoked") {
                assertFailsWith(NoSuchElementException::class) {
                    set.first()
                }
            }
        }

        describe("getting the first item") {
            val item = "foo"
            beforeEachTest {
                set.add(item)
            }

            describe("when set is not empty") {
                lateinit var result: String
                beforeEachTest {
                    result = set.first()
                }

                it("should return the first item") {
                    assertEquals(item, result)
                }
            }
        }
    }
})