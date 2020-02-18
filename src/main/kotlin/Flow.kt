import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

@ExperimentalCoroutinesApi
fun fooFlow(): Flow<String> = flow { // flow builder
    listOf("Olga", "Damien", "Dylan", "Jérémy", "Louis", "Ursula", "Kilian", "Alban", "Benjamin", "Pablo", "Yin", "Henri", "Anthony")
        .forEach {
            println("Will send $it")
            delay(150L)
            emit(it)
        }
}.flowOn(Dispatchers.IO)

val flowCoroutineScope = CoroutineScope(Dispatchers.Default)

@ExperimentalCoroutinesApi
fun main() = runBlocking {

    val myFlow = fooFlow()
        .filter { it.length <= 5 }
        .map { Pair(it, it.length) }



    val list = mutableListOf<Pair<String, Int>>()
    myFlow
        .collect {
            delay(200L)
            println("Collected=$it")
            list.add(it)
        }

    println("Final list =$list")
}


/**
val myFlow = listOf("Olga", "Damien", "Dylan", "Jérémy", "Louis", "Ursula", "Kilian", "Alban", "Benjamin", "Pablo", "Yin", "Henri", "Anthony")
.asFlow()
.filter { it.length <= 5 }
.map { Pair(it, it.length) }
        **/