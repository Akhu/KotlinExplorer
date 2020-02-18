import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Blocking
 */
fun doSomethingAsSequence() : Sequence<Int> {
    return sequence {
        (0..10).forEach {
            println("Sequence element will be yield $it")
            yield(it)
        }
    }
}

fun main() {

    println(listOf(0,1,2,3,4,5,6,7,8,9,10)
        .take(5)
        .groupBy { it % 2 == 0 }
        .values)

    println(doSomethingAsSequence()
        .take(5)
        .groupBy { it % 2 == 0 }
        .values
    )

    println(doSomethingAsSequence()
        .take(5)
        .groupBy { it % 2 == 0 }
        .values
    )
}
