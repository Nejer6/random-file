package com.github.nejer6.util

import com.github.nejer6.util.naturalsort.naturalSort
import java.io.File

fun File.previous(): File? {
    var parent = parentFile
    while (true) {
        if (parent == null) return null
        val foundFile = parent.findFirstFile()!!
        if (foundFile == this) {
            parent = parent.parentFile
        } else {
            return foundFile
        }
    }
}

fun File.findFirstFile(): File? {
    if (!isDirectory) {
        throw IllegalArgumentException("The provided path is not a directory: $absolutePath")
    }

    val filesAndDirs = listFiles()?.toList()?.naturalSort { it.name } ?: return null

    for (file in filesAndDirs) {
        if (file.isFile) {
            return file
        } else if (file.isDirectory) {
            val foundFile = file.findFirstFile()
            if (foundFile != null) {
                return foundFile
            }
        }
    }

    return null
}

fun File.previousFiles(): List<File> {
    val fileList = mutableListOf(this)
    var previousFile = this.previous()
    while (previousFile != null) {
        fileList.add(previousFile)
        previousFile = previousFile.previous()
    }
    return fileList.reversed()
}
