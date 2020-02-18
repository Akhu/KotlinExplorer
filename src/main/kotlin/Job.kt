import kotlinx.coroutines.*



fun main() = runBlocking {

    val job = launch {
        println("Coroutine start")
        delay(1000)
        println("Coroutine end")
    }
    println("Job status =${job.status()}")
    job.join() //Join the main job (aka its parent job)
    println("Job status =${job.status()}")
    println("Done")
}