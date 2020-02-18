
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

val coroutineScopeCustom = CoroutineScope(Dispatchers.IO)

@ExperimentalCoroutinesApi
fun CoroutineScope.fooChannelProducer(): ReceiveChannel<String> = produce { // flow builder
    listOf("Olga", "Damien", "Dylan", "Jérémy", "Louis", "Ursula", "Kilian", "Alban", "Benjamin", "Pablo", "Yin", "Henri", "Anthony")
        .forEach {
            logger.info("Will send $it")
            delay(150L)
            send(it)
        }
}

fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<String>) = launch {
    for (msg in channel) {
        if(msg.length <= 5){
            logger.info(Pair(msg, msg.length))
        }
        logger.info("Processor #$id received $msg")
    }
}

@ExperimentalCoroutinesApi
fun main() = runBlocking{
    coroutineScopeCustom.launch {
        val producer = fooChannelProducer()

        launchProcessor(1, producer)
        //repeat(5) { launchProcessor(it, producer) }

        delay(5000L)

        producer.cancel()


        println("Done !")
    }.join()
}