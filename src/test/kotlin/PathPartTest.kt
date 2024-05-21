import com.github.nejer6.PathPartImpl
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PathPartTest {

    @Test
    fun oneFile() {
        val path = "C:\\random\\a.txt"
        val file = File(path)
        val rootPart = PathPartImpl()
        rootPart.add(
            value = path,
            file
        )

        assertNull(rootPart.file)
        assertNull(rootPart.parent)
        assertEquals(1, rootPart.size)

        val filePart = rootPart.getFilePathPart(0)!!
        assertEquals(path, filePart.name)
        assertEquals(file, filePart.file)
        assertEquals(rootPart, filePart.parent)
        assertEquals(1, filePart.size)
        assertEquals(0, filePart.children.size)
    }

    @Test
    fun twoFiles() {
        val rootPart = PathPartImpl()

        val path2 = "C:\\random\\b.txt"
        val file2 = File(path2)
        rootPart.add(
            value = path2,
            file2
        )

        val path1 = "C:\\random\\a.txt"
        val file1 = File(path1)
        rootPart.add(
            value = path1,
            file1
        )

        assertEquals(2, rootPart.size)

        val file1Part = rootPart.getFilePathPart(0)!!
        assertEquals(file1, file1Part.file)

        val file2Part = rootPart.getFilePathPart(1)!!
        assertEquals(file2, file2Part.file)
    }

    @Test
    fun fourFiles() {
        val rootPart = PathPartImpl()

        val path1 = "C:\\random\\a1a\\a1a.txt"
        val file1 = File(path1)
        rootPart.add(
            value = path1,
            file1
        )

        val path2 = "C:\\random\\a1a\\a2b.txt"
        val file2 = File(path2)
        rootPart.add(
            value = path2,
            file2
        )

        val path3 = "C:\\random\\a2b\\a1a.txt"
        val file3 = File(path3)
        rootPart.add(
            value = path3,
            file3
        )

        val path4 = "C:\\random\\a2b\\a2b.txt"
        val file4 = File(path4)
        rootPart.add(
            value = path4,
            file4
        )

        val path5 = "C:\\random\\a.txt"
        val file5 = File(path5)
        rootPart.add(
            value = path5,
            file5
        )

        val path6 = "C:\\random\\a\\1.txt"
        val file6 = File(path6)
        rootPart.add(
            value = path6,
            file6
        )

        val path7 = "C:\\random\\a\\2.txt"
        val file7 = File(path7)
        rootPart.add(
            value = path7,
            file7
        )

        val path8 = "C:\\random\\a\\11.txt"
        val file8 = File(path8)
        rootPart.add(
            value = path8,
            file8
        )

        rootPart.print(0)

        val file1part = rootPart.getFilePathPart(0)!!
        val file2part = rootPart.getFilePathPart(1)!!
        val file3part = rootPart.getFilePathPart(2)!!
        val file4part = rootPart.getFilePathPart(3)!!
        val file5part = rootPart.getFilePathPart(4)!!

        assertEquals(file1, file1part.file)
        assertEquals(file2, file2part.file)
        assertEquals(file3, file3part.file)
        assertEquals(file4, file4part.file)
        assertEquals(file5, file5part.file)

        assertNull(file1part.previous())
        assertEquals(file1part, file2part.previous())
        assertEquals(file1part, file3part.previous())
        assertEquals(file3part, file4part.previous())
        assertEquals(file1part, file5part.previous())

        assertEquals(
            listOf(file1part, file3part, file4part),
            file4part.allPrevious()
        )
    }
}