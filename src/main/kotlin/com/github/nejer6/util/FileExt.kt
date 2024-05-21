package com.github.nejer6.util

import com.github.nejer6.util.naturalsort.naturalSort
import java.io.File

fun File.previous(
    filter: (File) -> Boolean
): File? {
    var parent = parentFile
    while (true) {
        if (parent == null) return null
        val foundFile = parent.findFirstFile(filter)
        if (foundFile == this) {
            parent = parent.parentFile
        } else {
            return foundFile
        }
    }
}

fun File.findFirstFile(
    filter: (File) -> Boolean
): File? {
    if (!isDirectory) {
        throw IllegalArgumentException("The provided path is not a directory: $absolutePath")
    }

    val filesAndDirs = listFiles()?.toList()?.filter(filter)?.naturalSort { it.name } ?: return null

    for (file in filesAndDirs) {
        if (file.isFile) {
            return file
        } else if (file.isDirectory) {
            val foundFile = file.findFirstFile(filter)
            if (foundFile != null) {
                return foundFile
            }
        }
    }

    return null
}

fun File.previousFiles(
    filter: (File) -> Boolean
): List<File> {
    val fileList = mutableListOf(this)
    var previousFile = previous(filter)
    while (previousFile != null) {
        fileList.add(previousFile)
        previousFile = previousFile.previous(filter)
    }
    return fileList.reversed()
}
