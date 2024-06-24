package com.github.nejer6

import com.github.nejer6.util.naturalsort.NaturalComparator
import java.io.File

class PathPartImpl : PathPart {

    constructor() {
        parent = null
        name = ""
        file = null
    }

    private constructor(
        name: String,
        file: File,
        parent: PathPart,
        other: String?
    ) {
        this.name = name
        this.parent = parent
        if (other != null) {
            this.file = null
            add(other, file)
        } else {
            length = 1
            this.file = file
        }
    }

    private var length = 0
    private val comparator = NaturalComparator()

    override val name: String
    override val file: File?
    override val children: MutableList<PathPart> = mutableListOf()
    override val size get() = length
    override val parent: PathPart?



    override fun add(value: String, file: File) {
        length++

        val isDigit = value.first().isDigit()
        val nameEnd = if (isDigit) {
            value.indexOfFirst { !it.isDigit() }
        } else {
            value.indexOfFirst { it.isDigit() }
        }

        val name: String
        val other: String?
        if (nameEnd == -1) {
            name = value
            other = null
        } else {
            name = value.substring(0, nameEnd)
            other = value.substring(nameEnd)
        }

        val position = children.map { it.name }.binarySearch(name, comparator)
        if (position < 0) {
            val childPathPart = PathPartImpl(
                name = name,
                file = file,
                parent = this,
                other = other
            )
            children.add(-position - 1, childPathPart)
        } else {
            children[position].add(other!!, file)
        }
    }

    override fun getFilePathPart(index: Int): PathPart? {
        if (this.file != null) return this

        var skipped = 0
        for (child in children) {
            val childIndex = index - skipped
            if (childIndex < child.size ) {
                return child.getFilePathPart(childIndex)
            } else if (childIndex == 0) {
                return child
            }
            skipped += child.size
        }
        return null
    }

    override fun getFilePathPart(absolutePath: String): PathPart? {
        val parts = absolutePath.split(File.separator).filter { it.isNotEmpty() }
        var current: PathPart = this
        for (part in parts) {
            val next = current.children.find { it.name == part }
            if (next == null) {
                return null
            }
            current = next
        }
        return current
    }

    override fun allPrevious(): List<PathPart> {
        val list = mutableListOf<PathPart>(this)
        var previousPart = previous()
        while (previousPart != null) {
            list.add(previousPart)
            previousPart = previousPart.previous()
        }
        return list.reversed()
    }

    override fun previous(): PathPart? {
        var parent = parent
        while (true) {
            if (parent == null) return null
            val foundFile = parent.firstFilePathPart()
            if (foundFile == this) {
                parent = parent.parent
            } else {
                return foundFile
            }
        }
    }

    override fun firstFilePathPart(): PathPart? {
        return if (file != null) {
            this
        } else {
            children.first().firstFilePathPart()
        }
    }

    override fun compareTo(other: PathPart): Int {
        return comparator.compare(this.name, other.name)
    }

    override fun print(depth: Int) {
        for (i in 0.. depth) {
            print("\t")
        }
        println(name)
        for (child in children) {
            child.print(depth + 1)
        }
    }
}
