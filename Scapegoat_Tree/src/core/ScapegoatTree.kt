package core

import java.lang.IllegalArgumentException
import java.util.*

class ScapegoatTree<T : Comparable<T>>(
    a: Double = 1.0, private val delegate: ScapegoatTree<T>? = null, private val from: T? = null,
    private val to: T? = null
) : SortedSet<T> {
    private var root: Node<T>? = null
    private var alpha = if (a >= 1 / 2 && a <= 1) a else 1.0
    override var size: Int = if (delegate != null) {
        when {
            from != null && to != null -> delegate.count { it >= from && it < to }
            from != null -> delegate.count { it >= from }
            to != null -> delegate.count { it < to }
            else -> delegate.size
        }
    } else 0
        get() = if (delegate != null) findCurrentSize() else field

    private var maxDepth: Double =
        Math.log(size.toDouble()) / Math.log(1 / alpha)

    inner class Node<T>(var value: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null
        var parent: Node<T>? = null
        var depth: Int = -1
        fun getSize(): Int {
            var res = 1
            if (this.left != null) res += this.left!!.getSize()
            if (this.right != null) res += this.right!!.getSize()
            return res
        }
    }

    private fun findCurrentSize(): Int {
        val temp = when {
            from != null && to != null -> delegate!!.count { it >= from && it < to }
            from != null -> delegate!!.count { it >= from }
            to != null -> delegate!!.count { it < to }
            else -> delegate!!.size
        }
        this.size = temp
        return temp
    }

    fun getRoot(): Node<T>? {
        return root
    }

    fun setAlpha(alpha: Double) {
        this.alpha = alpha
        this.maxDepth = Math.log(size.toDouble()) / Math.log(1 / alpha)
        if (root != null && findCurrentMaxDepth() > maxDepth) {
            rebuiltTree(root!!)
        }
    }

    override fun add(element: T): Boolean {
        if (delegate != null) {
            when {
                from != null && to != null -> {
                    if (element >= from && element < to) {
                        return delegate.add(element)
                    } else throw IllegalArgumentException()
                }
                from != null -> {
                    if (element >= from) {
                        return delegate.add(element)
                    } else throw IllegalArgumentException()
                }
                to != null -> {
                    if (element < to) {
                        return delegate.add(element)
                    } else throw IllegalArgumentException()
                }
                else -> {
                    return delegate.add(element)
                }
            }
        }
        val closest = find(element)
        val comparison = if (closest == null) -1 else element.compareTo(closest.value)
        if (comparison == 0) {
            return false
        }
        val newNode = Node(element)
        when {
            closest == null -> {
                newNode.depth = 0
                root = newNode
            }
            comparison < 0 -> {
                assert(closest.left == null)
                newNode.parent = closest
                newNode.depth = closest.depth + 1
                closest.left = newNode
            }
            else -> {
                assert(closest.right == null)
                newNode.parent = closest
                newNode.depth = closest.depth + 1
                closest.right = newNode
            }
        }
        size++
        this.maxDepth = Math.log(size.toDouble()) / Math.log(1 / alpha)
        if (newNode.depth > maxDepth) {
            rebuiltTree(findScapegoat(newNode))
        }
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        var check = false
        for (elem in elements) {
            if (this.add(elem)) check = true
        }
        return check
    }

    private fun findScapegoat(node: Node<T>): Node<T> {
        var n = node
        while (n.getSize() / n.parent!!.getSize().toDouble() <= alpha) {
            n = n.parent!!
        }
        return n.parent!!
    }

    private fun rebuiltTree(node: Node<T>) {
        var from = node
        var to = node
        while (from.left != null) from = from.left!!
        while (to.right != null) to = to.right!!
        val listFromScapegoat = subSet(from.value, to.value).toMutableList()
        listFromScapegoat.add(to.value)
        val res = builtFromList(listFromScapegoat, node.depth)
        if (node == root) root = res
        else {
            res?.parent = node.parent
            if (node.parent?.right?.value == node.value) node.parent?.right = res
            else node.parent?.left = res
        }
    }

    private fun builtFromList(list: MutableList<T>, depth: Int): Node<T>? {
        if (list.size == 0) return null
        if (list.size == 1) return Node(list[0])
        val mid = if (list.size % 2 != 0) list.size / 2 else list.size / 2 - 1
        val newRoot = Node(list[mid])
        newRoot.depth = depth
        val leftPart = builtFromList(list.subList(0, mid), newRoot.depth + 1)
        leftPart?.parent = newRoot
        leftPart?.depth = newRoot.depth + 1
        val rightPart = builtFromList(list.subList(mid + 1, list.size), newRoot.depth + 1)
        rightPart?.parent = newRoot
        rightPart?.depth = newRoot.depth + 1
        newRoot.left = leftPart
        newRoot.right = rightPart
        return newRoot
    }

    fun findCurrentMaxDepth(): Int {
        val bi = this.iterator()
        var max: Int = -1
        while (bi.hasNext()) {
            val curr = bi.findNext()!!.depth
            if (curr > max) max = curr
        }
        return max
    }

    private fun find(value: T): Node<T>? =
        root?.let { find(it, value) }


    private fun find(start: Node<T>, value: T): Node<T> {
        if (delegate != null) {
        }
        val comparison = value.compareTo(start.value)
        return when {
            comparison == 0 -> start
            comparison < 0 -> {
                if (delegate != null) {
                    if (start.left == null) return start
                    if (from != null && start.left!!.value < from) {
                        var curr: Node<T>? = start.left!!
                        while (curr!!.right != null) {
                            curr = curr.right
                            if (curr!!.value >= from) return find(curr, value)
                        }
                        return start
                    }
                }
                start.left?.let { find(it, value) } ?: start
            }
            else -> {
                if (delegate != null) {
                    if (start.right == null) return start
                    if (to != null && start.right!!.value >= to) {
                        var curr: Node<T>? = start.right!!
                        while (curr!!.left != null) {
                            curr = curr.left
                            if (curr!!.value < to) return find(curr, value)
                        }
                        return start
                    }
                }
                start.right?.let { find(it, value) } ?: start
            }
        }
    }

    override fun clear() {
        size = 0
        root = null
    }

    inner class BinaryTreeIterator : MutableIterator<T> {
        private var current: Node<T>? = null
        private var stack: Stack<Node<T>> = Stack()
        private val delegateIter = delegate?.iterator()
        private var nextSubSetElem: Node<T>? = null

        init {
            if (delegateIter != null) {
                while (delegateIter.hasNext()) {
                    val next = delegateIter.findNext()
                    if (from != null && to != null) {
                        if (next!!.value >= from && next.value < to) {
                            nextSubSetElem = next
                            break
                        }
                    } else if (from != null) {
                        if (next!!.value >= from) {
                            nextSubSetElem = next
                            break
                        }
                    } else if (to != null) {
                        if (next!!.value < to) {
                            nextSubSetElem = next
                            break
                        }
                    } else {
                        nextSubSetElem = next
                        break
                    }
                }
            } else {
                current = root
                if (root != null) stack.push(root)
                while (current?.left != null) {
                    current = current?.left
                    stack.push(current)
                }
            }

        }

        fun findNext(): Node<T>? {
            if (delegate != null) {
                if (nextSubSetElem == null) return null
                val res = nextSubSetElem
                nextSubSetElem = null
                val next = delegateIter?.findNext()
                if (next != null) {
                    if (from != null && to != null) {
                        if (next.value >= from && next.value < to) {
                            nextSubSetElem = next
                        }
                    } else if (from != null) {
                        if (next.value >= from) {
                            nextSubSetElem = next
                        }
                    } else if (to != null) {
                        if (next.value < to) {
                            nextSubSetElem = next
                        }
                    } else {
                        nextSubSetElem = next
                    }
                }
                return res
            }
            if (stack.isEmpty()) return null
            current = stack.pop()
            var temp = current
            if (temp?.right != null) {
                temp = temp.right
                stack.push(temp)
                while (temp?.left != null) {
                    temp = temp.left
                    stack.push(temp)
                }
            }
            return current
        }


        override fun hasNext(): Boolean {
            if (delegate != null) return nextSubSetElem != null
            return stack.isNotEmpty()
        }


        override fun next(): T {
            if (delegateIter != null) {
                return findNext()?.value ?: throw NoSuchElementException()
            }
            findNext()
            return (current ?: throw NoSuchElementException()).value
        }

        override fun remove() {
            if (current != null) remove(current!!.value)
        }
    }

    override fun iterator(): BinaryTreeIterator = BinaryTreeIterator()


    override fun remove(element: T): Boolean {
        if (delegate != null) {
            return when {
                from != null && to != null -> {
                    if (element >= from && element < to) delegate.remove(element)
                    else false
                }
                from != null -> {
                    if (element >= from) delegate.remove(element)
                    else false
                }
                to != null -> {
                    if (element < to) delegate.remove(element)
                    else false
                }
                else -> delegate.remove(element)
            }
        }
        if (root == null) return false
        val del = delete(root!!, element)
        if (del != null) root = del else return false
        size--
        this.maxDepth = Math.log(size.toDouble()) / Math.log(1 / alpha)
        if (findCurrentMaxDepth() > maxDepth) {
            rebuiltTree(root!!)
        }
        return true
    }

    private fun delete(node: Node<T>, value: T): Node<T>? {
        val comp = value.compareTo(node.value)
        when {
            comp > 0 -> node.right = delete(node.right!!, value)
            comp < 0 -> node.left = delete(node.left!!, value)
            else -> when {
                (node.left != null && node.right != null) -> {
                    var temp = node.right
                    while (temp?.left != null) temp = temp.left
                    node.value = temp!!.value
                    node.right = delete(node.right!!, temp.value)
                }
                node.left != null -> {
                    node.left?.parent = node.parent
                    node.left?.depth = node.depth
                    return node.left
                }
                else -> {
                    node.right?.parent = node.parent
                    node.right?.depth = node.depth
                    return node.right
                }
            }
        }
        return node
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var check = false
        for (elem in elements) {
            if (this.remove(elem)) check = true
        }
        return check
    }

    override fun contains(element: T): Boolean {
        if (delegate != null) {
            return when {
                from != null && to != null -> {
                    if (element >= from && element < to) delegate.contains(element) else false
                }
                from != null -> {
                    if (element >= from) delegate.contains(element) else false
                }
                to != null -> {
                    if (element < to) delegate.contains(element) else false
                }
                else -> delegate.contains(element)
            }
        }
        val closest = find(element)
        return closest != null && element.compareTo(closest.value) == 0
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (elem in elements) {
            if (!this.contains(elem)) return false
        }
        return true
    }

    override fun comparator(): Comparator<in T>? = null

    override fun first(): T {
        if (delegate != null) return this.iterator().next()
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.left != null) {
            current = current.left!!
        }
        return current.value
    }

    override fun last(): T {
        if (delegate != null) {
            val bi = this.iterator()
            var res = bi.next()
            while (bi.hasNext()) {
                res = bi.next()
            }
            return res
        }
        var current: Node<T> = root ?: throw NoSuchElementException()
        while (current.right != null) {
            current = current.right!!
        }
        return current.value
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var check = false
        for (elem in this) {
            if (!elements.contains(elem)) {
                this.remove(elem)
                check = true
            }
        }
        return check
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun headSet(toElement: T): SortedSet<T> {
        if ((this.from != null && toElement < this.from) || (this.to != null && toElement > this.to)) throw IllegalArgumentException()
        return if (delegate != null) ScapegoatTree(alpha, delegate, to = toElement)
        else ScapegoatTree(alpha, this, to = toElement)
    }

    override fun tailSet(fromElement: T): SortedSet<T> {
        if ((this.to != null && fromElement > this.to) || (this.from != null && fromElement < this.from)) throw IllegalArgumentException()
        return if (delegate != null) ScapegoatTree(alpha, delegate, from = fromElement)
        else ScapegoatTree(alpha, this, from = fromElement)
    }

    override fun subSet(fromElement: T, toElement: T): SortedSet<T> {
        if (toElement < fromElement) throw IllegalArgumentException()
        if ((this.from != null && fromElement < this.from) || (this.to != null && toElement > this.to)) throw IllegalArgumentException()
        return if (delegate != null) ScapegoatTree(alpha, delegate, fromElement, toElement)
        else ScapegoatTree(alpha, this, fromElement, toElement)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Set<*>) return false
        if (this.size != other.size) return false
        for (el in this) {
            if (!other.contains(el)) return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = 0
        for (el in this) {
            result += el.hashCode()
        }
        return result
    }

    override fun toString(): String {
        return this.toMutableSet().toString()
    }
}