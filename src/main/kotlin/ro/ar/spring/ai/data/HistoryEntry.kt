package ro.ar.spring.ai.data

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class HistoryEntry(
    @Id val id: ObjectId? = null,
    val promptMessage: String,
    val response: String,
    val username: String
) {

    override fun toString(): String {
        return "history_entry:" +
                "prompt:'$promptMessage' " +
                "response:'$response')"
    }
}