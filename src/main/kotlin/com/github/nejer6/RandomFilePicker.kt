package com.github.nejer6

import java.io.File

class RandomFilePicker(
    private val directory: File
) {

    private val files = mutableListOf<File>()

    init {
        collectFiles(directory)
    }

    fun getRandomFile(): File? {
        if (files.isEmpty()) return null
        return files.random()
    }

    fun updateFiles() {
        files.clear()
        collectFiles(directory)
    }

    private fun collectFiles(directory: File) {
        val files = directory.listFiles()!!
        files.forEach { file ->
            if (file.isDirectory) {
                collectFiles(file)
            } else {
                this.files.add(file)
            }
        }
    }
}