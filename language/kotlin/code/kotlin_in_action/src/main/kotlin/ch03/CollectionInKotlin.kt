package ch03

import java.util.*
import java.util.concurrent.ConcurrentHashMap

object CollectionInKotlin {
    @JvmStatic
    fun main(args: Array<String>) {
        val map: Map<String, String> = ConcurrentHashMap()
        val queue: Queue<String> = LinkedList()
        val arrayDeque = ArrayDeque<String>()

        val stringArray1 = arrayOf("a", "asd", "vbn")
        val stringArray2 = arrayOfNulls<String>(3)
        val intArray1 = arrayOf(1,2,3)
        val intArray2 = arrayOfNulls<Int>(4)

    }
}

fun main() {
    val map = mapOf(1 to "one", 2 to "two", 7 to "seven")
    println(map[2])
    println(map.get(2))
    val map2 = mapOf("hello" to "world")
    println(map2["hello"])
}