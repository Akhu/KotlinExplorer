import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager

fun main() = runBlocking {

    GlobalScope.launch {
        logger.info("1. I am running on a coroutine !")
    }

    logger.info("2. I am not blocked by the coroutine")

    val result = GlobalScope.async {
        "3. I am running on an async coroutine"
    }


    logger.info("Result of async='${result.await()}'")

}