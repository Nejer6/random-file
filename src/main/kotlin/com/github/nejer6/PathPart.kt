package com.github.nejer6

import java.io.File

interface PathPart : Comparable<PathPart> {

    val name: String
    val parent: PathPart?
    val file: File?
    val children: List<PathPart>
    val size: Int

    fun add(value: String, file: File)

    fun getFilePathPart(index: Int): PathPart?

    fun allPrevious(): List<PathPart>

    fun previous(): PathPart?

    fun firstFilePathPart(): PathPart?

    fun print(depth: Int)
}
