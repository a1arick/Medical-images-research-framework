package core.pipeline

import core.common.Event

class DummyPipeKeeper : PipelineKeeper {

    override val session: IPipelineSession
        get() = DummySession

    override fun getCachedDataFor(sender: Any, request: PipelineKeeperRequest): Any? {
        return null
    }

    companion object DummySession : IPipelineSession {
        override val newRecord: Event<PipelineSessionRecord>
            get() = Event(mutableListOf())

        override fun addSuccess(message: String) {}

        override fun addWarning(message: String) {}

        override fun addError(message: String) {}

        override fun addNew(message: String): PipelineSessionRecord = PipelineSessionRecord(PipelineSessionRecord.RecordLevel.InProgress, message)
    }
}
