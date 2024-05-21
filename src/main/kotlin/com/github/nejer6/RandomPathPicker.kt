package com.github.nejer6

import java.io.File

class RandomPathPicker(
    private val directory: File,
    private val filter: (File) -> Boolean
) {

    private var rootPath = PathPartImpl()

    init {
        collectFiles(directory)
        rootPath.print(0)
    }

    fun getRandomFiles(): List<File> {
        val index = randomIndex()
        val randomPaths = rootPath.getFilePathPart(index)!!.allPrevious()
        return randomPaths.map { it.file!! }
    }

    fun updateFiles() {
        rootPath = PathPartImpl()
        rootPath.print(0)
        collectFiles(directory)
    }

    private fun randomIndex(): Int {
        return (0..<rootPath.size).random()
    }

    private fun collectFiles(directory: File) {
        val files = directory.listFiles()!!
        files.forEach { file ->
            if (file.isDirectory) {
                collectFiles(file)
            } else {
                if (filter(file)) {
                    rootPath.add(
                        value = file.absolutePath,
                        file = file
                    )
                }
            }
        }
    }
}