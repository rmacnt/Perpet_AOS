package pet.perpet.equal.presentation.subscribe

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pet.perpet.domain.model.address.Juso
import pet.perpet.domain.model.product.Product
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.model.profile.Disease
import pet.perpet.framework.util.Logger

object SubscribeChannel {

    val productClick
        get() = Provider.get<Product>("productClick")

    object Provider {

        private val map = hashMapOf<String, Item<*>>()

        fun <Value : Any> get(key: String): Item<Value> {
            return synchronized(map) {
                map.getOrPut("Provider:${key}", {
                    Item<Value>()
                }) as Item<Value>
            }
        }

        fun clear() {
            try {
                map.entries.asFlow().onEach {
                    it.value.close()
                }
                map.clear()
            } catch (e: java.lang.Exception) {
                Logger.printStackTrace(e)
            }
        }
    }

    class Item<Value> {

        private val channel: Channel<Value> = Channel()

        private val ioScope by lazy {
            CoroutineScope(Dispatchers.IO)
        }

        fun send(value: Value) {
            ioScope.launch {
                lw("asyncSend step 1 > $value")
                channel.send(value)
            }
        }

        fun consumeEach(result: (value: Value?) -> Unit) {
            ioScope.launch {
                channel.consumeEach {
                    lw("consumeEach step 1")
                    launch(Dispatchers.Main) {
                        lw("consumeEach step 2")
                        result.invoke(it)
                    }
                }
            }
        }

        fun close() {
            channel.close()
            ioScope.cancel()
        }

        private fun lw(message: String) {
            Logger.w("Channel.Provider", message)
        }
    }
}