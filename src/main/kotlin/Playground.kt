import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    GlobalScope.launch {
        logger.info("This coroutine do some job")
        delay(500)
        logger.info("Coroutine job is done !")
    }

    logger.info("Pretend I am doing something on the main thread")
    Thread.sleep(1000L)
    logger.info("I am doing another business here")
}