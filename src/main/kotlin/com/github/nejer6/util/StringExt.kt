package com.github.nejer6.util

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime

fun String.getCreationTime(): FileTime {
    val path = Paths.get(this)
    val attributes = Files.readAttributes(path, BasicFileAttributes::class.java)
    val creationTime = attributes.creationTime()
    return creationTime
}
