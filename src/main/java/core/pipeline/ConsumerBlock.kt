package core.pipeline

import core.common.Event
import core.data.Data
import core.pipeline.PipelineBlock

import java.util.function.Consumer


/**
 * A [PipelineBlock], that is only executing [<] provided.
 * Subscription to this block is not allowed and will lead to an error.
 */
class ConsumerBlock(
        private val consumer: (Data) -> Unit,
        name: String = "Consumer",
        pipelineKeeper: PipelineKeeper = DummyPipeKeeper()) : PipelineBlock<Data, Data>(name, pipelineKeeper) {

    override fun flush() {}

    override fun inputReady(sender: Any, input: Data) {
        consumer(input)
    }

    override val dataReady: Event<Data>
        get() = throw PipelineException("Subscription to 'data ready' event of consumer block is forbidden")
}
