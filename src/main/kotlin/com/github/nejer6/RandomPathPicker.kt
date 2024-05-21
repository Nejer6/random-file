package com.github.nejer6

import java.io.File

class RandomPathPicker(
    private val directory: File
) {

    private var rootPath = PathPartImpl()

    init {
        collectFiles(directory)
    }

    fun getRandomPart(): PathPart? {
        val size = rootPath.size
        if (size == 0) return null
        val index = randomIndex()
        var randomPath = rootPath.getFilePathPart(index)!!
        if (!randomPath.file!!.exists()) {
            updateFiles()
            randomPath = rootPath.getFilePathPart(randomIndex())!!
        }

        return randomPath
    }

    private fun randomIndex(): Int {
        return (0..<rootPath.size).random()
    }

    private fun updateFiles() {
        rootPath = PathPartImpl()
        collectFiles(directory)
    }

    private fun collectFiles(directory: File) {
        val files = directory.listFiles()!!
        files.forEach { file ->
            if (file.isDirectory) {
                collectFiles(file)
            } else {
                rootPath.add(
                    value = file.absolutePath,
                    file = file
                )
            }
        }
    }
}