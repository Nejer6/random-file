package com.github.nejer6

import com.github.nejer6.util.getCreationTime
import java.io.File
import java.time.Instant
import kotlin.random.Random

class RandomPathPicker(
    private val directory: File,
    private val filter: (File) -> Boolean
) {

    private var rootPath: PathPart = PathPartImpl()
    private val files = mutableListOf<File>()

    init {
        collectFiles(directory)
        rootPath.print(0)
    }

    fun getRandomFiles(
        usingDate: Boolean
    ): List<File> {
        val randomPath = if (usingDate) {
            val randomFile = selectWeightedRandomFile()
            val absolutePath = randomFile.absolutePath
            rootPath.getFilePathPart(absolutePath)
        } else {
            val index = randomIndex()
            rootPath.getFilePathPart(index)
        }
        val randomPaths = randomPath!!.allPrevious()
        return randomPaths.map { it.file!! }
    }

    fun updateFiles() {
        files.clear()
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
                    this.files.add(file)
                    rootPath.add(
                        value = file.absolutePath,
                        file = file
                    )
                }
            }
        }
    }

    private fun selectWeightedRandomFile(): File {
        val currentTime = Instant.now().toEpochMilli()

        val weights = files.map { file ->
            val creationTime = file.path.getCreationTime()
            val timeSinceCreation = currentTime - creationTime.toInstant().toEpochMilli()
            1.0 / timeSinceCreation
        }

        val totalWeight = weights.sum()
        val probabilities = weights.map { weight -> weight / totalWeight }

        val cumulativeProbabilities = probabilities.scan(0.0) { acc, probability ->
            acc + probability
        }.drop(1)

        val randomValue = Random.nextDouble()

        for ((index, cumulativeProbability) in cumulativeProbabilities.withIndex()) {
            if (randomValue <= cumulativeProbability) {
                return files[index]
            }
        }

        return files.last()
    }
}
