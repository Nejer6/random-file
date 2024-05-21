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
        var index = randomIndex()
        var randomPaths = rootPath.getFilePathPart(index)!!.allPrevious()
        for (randomPath in randomPaths) {
            if (!randomPath.file!!.exists()) {
                updateFiles()
                index = randomIndex()
                randomPaths = rootPath.getFilePathPart(index)!!.allPrevious()
                break
            }
        }
        return randomPaths.map { it.file!! }
    }

    private fun randomIndex(): Int {
        return (0..<rootPath.size).random()
    }

    private fun updateFiles() {
        rootPath = PathPartImpl()
        rootPath.print(0)
        collectFiles(directory)
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