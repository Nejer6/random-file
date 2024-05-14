package com.github.nejer6

import java.io.File

fun getRandomFileName(directoryPath: String): String {
    val directory = File(directoryPath)
    val fileList = mutableListOf<File>()
    collectFiles(directory, fileList)
}