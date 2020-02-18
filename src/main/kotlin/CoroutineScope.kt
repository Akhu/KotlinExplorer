import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KProperty

val logger: org.apache.logging.log4j.Logger = LogManager.getLogger("KotlinExplorer")

fun printlnd(msg: String) = println("[${Thread.currentThread().name}] $msg")

val sampleCoroutineScope = CoroutineScope(Dispatchers.IO)

val handler = CoroutineExceptionHandler { _, exception ->
    println("A coroutine thrown an exception=${exception.message}")
}

val managerJob = SupervisorJob()

val managerCoroutineScope = CoroutineScope(managerJob + Dispatchers.IO + handler)


fun main() {
    val result = managerCoroutineScope.async {
        var resultFromLaunch = 0
        launch {
            resultFromLaunch = doSomething()
            println("Function has returned=$resultFromLaunch")
        }

        val result = async {
            doSomething()
        }
        println("Function has returned from Async=${result.await()}")
        result.await() + resultFromLaunch
    }

    runBlocking {
        println("Final result = ${result.await()}")
    }

    managerCoroutineScope.launch {
        throw Exception("This coroutine has failed 💀")
    }

    runBlocking {
        delay(5000L)
    }

}

suspend fun doSomething() : Int{
    delay(1000L)
    return 10
}



fun Job.status(): String = when {
    isCancelled -> "cancelled"
    isActive -> "Active"
    isCompleted -> "Complete"
    else -> "Nothing"
}
