import org.junit.Test
import core.ScapegoatTree
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class ScapegoatTreeTest {

    @Test
    fun testAdd() {
        val tree = ScapegoatTree<Int>(1 / 2.0)
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    fun testRemove() {
        val random = Random()
        for (iteration in 1..1000) {
            val list = mutableListOf<Int>()
            for (i in 1..200) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = ScapegoatTree<Int>(2 / 3.0)
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            binarySet.remove(toRemove)
            println("Removing $toRemove from $list")
            assertEquals<SortedSet<*>>(treeSet, binarySet.toSortedSet(), "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
        }
    }

    @Test
    fun testIterator() {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..1000) {
                list.add(random.nextInt(1000))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = ScapegoatTree<Int>(2 / 3.0)
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                val a = binaryIt.next()
                assertEquals(treeIt.next(), a)
            }
        }
        val treeSet = TreeSet<Int>()
        val binarySet = ScapegoatTree<Int>(2 / 3.0)
        val treeIt = treeSet.iterator()
        val binaryIt = binarySet.iterator()
        while (treeIt.hasNext()) {
            assertEquals(treeIt.next(), binaryIt.next())
        }
    }

    @Test
    fun testIteratorRemove() {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = ScapegoatTree<Int>(2 / 3.0)
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            while (iterator.hasNext()) {
                val element = iterator.next()
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet.toSortedSet(), "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size)
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
        }
    }

    private var tree = ScapegoatTree<Int>()
    private fun fillTree() {
        tree = ScapegoatTree(2 / 3.0)
        tree.add(5)
        tree.add(1)
        tree.add(2)
        tree.add(7)
        tree.add(9)
        tree.add(10)
        tree.add(8)
        tree.add(4)
        tree.add(3)
        tree.add(6)
    }

    private var scapegoatTree = ScapegoatTree<Int>(2 / 3.0)

    @Test
    fun scapegoatAddTest() {
        val scapegoatTree = ScapegoatTree<Double>(2 / 3.0)
        scapegoatTree.add(7.0)
        scapegoatTree.add(8.0)
        scapegoatTree.add(6.0)
        scapegoatTree.add(9.0)
        scapegoatTree.add(5.0)
        scapegoatTree.add(2.0)
        scapegoatTree.add(4.0)
        scapegoatTree.add(1.0)
        scapegoatTree.add(0.0)
        scapegoatTree.add(3.0)
        assertEquals(scapegoatTree.findCurrentMaxDepth(), 5)
        scapegoatTree.add(3.5)
        assertEquals(scapegoatTree.findCurrentMaxDepth(), 4)
        assertTrue(scapegoatTree.contains(5.0))
        assertTrue(scapegoatTree.contains(3.5))
    }

    private fun fillScapegoatTree() {
        scapegoatTree = ScapegoatTree(2 / 3.0)
        scapegoatTree.add(1)
        scapegoatTree.add(5)
        scapegoatTree.add(4)
        scapegoatTree.add(6)
        scapegoatTree.add(7)
        scapegoatTree.add(8)
    }

    @Test
    fun subSizeTest() {
        fillScapegoatTree()
        val subSet = scapegoatTree.subSet(4, 7)
        assertEquals(6, scapegoatTree.size)
        assertEquals(3, subSet.size)
        scapegoatTree.clear()
        assertEquals(0, scapegoatTree.size)
        assertEquals(0, subSet.size)
        scapegoatTree.add(5)
        assertEquals(1, scapegoatTree.size)
        assertEquals(1, subSet.size)
        scapegoatTree.add(8)
        assertEquals(2, scapegoatTree.size)
        assertEquals(1, subSet.size)
    }
    @Test
    fun scapegoatRemoveTest() {
        fillScapegoatTree()
        assertEquals(scapegoatTree.findCurrentMaxDepth(), 4)
        scapegoatTree.remove(4)
        assertEquals(scapegoatTree.findCurrentMaxDepth(), 2)
        assertTrue(scapegoatTree.contains(5))
        assertFalse(scapegoatTree.contains(4))
    }

    @Test
    fun retainAllTest() {
        val scapegoatTree = ScapegoatTree<Double>(2 / 3.0)
        scapegoatTree.add(7.0)
        scapegoatTree.add(8.0)
        scapegoatTree.add(6.0)
        scapegoatTree.add(9.0)
        scapegoatTree.add(5.0)
        scapegoatTree.add(2.0)
        scapegoatTree.add(4.0)
        scapegoatTree.add(1.0)
        scapegoatTree.add(0.0)
        scapegoatTree.add(3.0)
        scapegoatTree.retainAll(listOf(5.0, 0.0, 6.0, 10.0))
        assertTrue(scapegoatTree.contains(5.0))
        assertTrue(scapegoatTree.contains(0.0))
        assertTrue(scapegoatTree.contains(6.0))
        assertFalse(scapegoatTree.contains(10.0))
        assertFalse(scapegoatTree.contains(8.0))
        assertFalse(scapegoatTree.contains(2.0))
    }

    @Test
    fun lastTest() {
        fillScapegoatTree()
        assertEquals(8, scapegoatTree.last())
    }

    @Test
    fun firstTest() {
        fillScapegoatTree()
        assertEquals(1, scapegoatTree.first())
    }

    @Test
    fun containsTest() {
        fillScapegoatTree()
        scapegoatTree.remove(4)
        assertTrue(scapegoatTree.contains(1))
        assertTrue(scapegoatTree.contains(5))
        assertFalse(scapegoatTree.contains(10))
        assertFalse(scapegoatTree.contains(4))
    }

    @Test
    fun findCurrentMaxDepthTest() {
        val scapegoatTree = ScapegoatTree<Int>(1.0)
        assertEquals(-1, scapegoatTree.findCurrentMaxDepth())
        scapegoatTree.add(1)
        scapegoatTree.add(2)
        scapegoatTree.add(3)
        scapegoatTree.add(5)
        scapegoatTree.add(7)
        scapegoatTree.add(8)
        assertEquals(5, scapegoatTree.findCurrentMaxDepth())
    }

    @Test
    fun doHeadSetTest() {
        fillTree()
        var set: SortedSet<Int> = tree.headSet(5)
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(false, set.contains(5))
        assertEquals(false, set.contains(6))
        assertEquals(false, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))
        set = tree.headSet(1)
        assertEquals(false, set.contains(1))
        set = tree.headSet(127)
        for (i in 1..10)
            assertEquals(true, set.contains(i))
        for (i in 11..1000) {
            tree.add(i)
        }
        set = tree.headSet(765)
        assertEquals(false, set.contains(999))
        assertEquals(false, set.contains(765))
        assertEquals(true, set.contains(1))
        assertEquals(true, set.contains(764))
        assertEquals(true, set.contains(333))


    }

    @Test
    fun doTailSetTest() {
        fillTree()
        var set: SortedSet<Int> = tree.tailSet(5)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(false, set.contains(3))
        assertEquals(false, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(true, set.contains(8))
        assertEquals(true, set.contains(9))
        assertEquals(true, set.contains(10))
        set = tree.tailSet(10)
        assertEquals(true, set.contains(10))
        assertEquals(false, set.contains(9))

        set = tree.tailSet(-128)
        for (i in 1..10)
            assertEquals(true, set.contains(i))

        for (i in 11..1000) {
            tree.add(i)
        }
        set = tree.tailSet(765)
        assertEquals(true, set.contains(999))
        assertEquals(true, set.contains(765))
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(764))
        assertEquals(false, set.contains(333))

    }

    @Test
    fun doHeadSetRelationTest() {
        fillTree()
        val set: SortedSet<Int> = tree.headSet(15)
        assertEquals(10, set.size)
        assertEquals(10, tree.size)
        tree.add(0)
        assertTrue(set.contains(0))
        set.add(13)
        assertTrue(tree.contains(13))
        set.remove(4)
        assertFalse(tree.contains(4))
        tree.remove(6)
        assertFalse(set.contains(6))
        tree.add(16)
        assertFalse(set.contains(16))
        assertEquals(10, set.size)
        assertEquals(11, tree.size)
    }

    @Test
    fun doTailSetRelationTest() {
        fillTree()
        val set: SortedSet<Int> = tree.tailSet(4)
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.remove(4)
        assertFalse(tree.contains(4))
        tree.remove(6)
        assertFalse(set.contains(6))
        tree.add(0)
        assertFalse(set.contains(0))
        assertEquals(6, set.size)
        assertEquals(10, tree.size)
    }

    @Test
    fun doSubSetTest() {
        fillTree()
        var set: SortedSet<Int> = tree.subSet(3, 8)
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(2))
        assertEquals(true, set.contains(3))
        assertEquals(true, set.contains(4))
        assertEquals(true, set.contains(5))
        assertEquals(true, set.contains(6))
        assertEquals(true, set.contains(7))
        assertEquals(false, set.contains(8))
        assertEquals(false, set.contains(9))
        assertEquals(false, set.contains(10))
        try {
            tree.subSet(4, 2)
            assertEquals(1, 0)
        } catch (e: IllegalArgumentException) {
            assertEquals(1, 1)
        }
        try {
            tree.subSet(-9, -10)
            assertEquals(1, 0)
        } catch (e: IllegalArgumentException) {
            assertEquals(1, 1)
        }
        set = tree.subSet(-4, 0)
        assertEquals(set.size, 0)
        for (i in 11..1000) {
            tree.add(i)
        }
        set = tree.subSet(555, 876)
        assertEquals(true, set.contains(555))
        assertEquals(true, set.contains(875))
        assertEquals(false, set.contains(554))
        assertEquals(false, set.contains(1))
        assertEquals(false, set.contains(1000))
        assertEquals(false, set.contains(876))
    }

    @Test
    fun doSubSetRelationTest() {
        fillTree()
        val set: SortedSet<Int> = tree.subSet(3, 15)
        assertEquals(8, set.size)
        assertEquals(10, tree.size)
        tree.add(12)
        assertTrue(set.contains(12))
        set.remove(3)
        assertFalse(tree.contains(3))
        tree.remove(6)
        assertFalse(set.contains(6))
        tree.add(0)
        assertFalse(set.contains(0))
        assertEquals(7, set.size)
        assertEquals(10, tree.size)
    }
}