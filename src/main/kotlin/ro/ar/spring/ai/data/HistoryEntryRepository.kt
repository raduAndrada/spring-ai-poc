package ro.ar.spring.ai.data

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryEntryRepository: MongoRepository<HistoryEntry, Long> {

    fun findByUsername(username: String): List<HistoryEntry>

}

