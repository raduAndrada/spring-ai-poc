package ro.ar.spring.ai.service

import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.ollama.OllamaChatModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ro.ar.spring.ai.data.HistoryEntry
import ro.ar.spring.ai.data.HistoryEntryRepository
import ro.ar.spring.ai.models.OllamaRequest
import ro.ar.spring.ai.models.OllamaResponse


@Service
class OllamaChatbotService(
    @Autowired val chatModel: OllamaChatModel,
    @Autowired val historyEntryRepository: HistoryEntryRepository
) {

    val PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS: String = """        
    The object `conversational_history` below represents the past interaction between you and the user.
    Each `history_entry` is represented as a pair of `prompt` and `response`.
    `prompt` is a past user prompt and `response` was your response for that `prompt`.
        
    Use the information in `conversational_history` if you need to recall things from the conversation
    , or in other words, if the `user_main_prompt` needs any information from past `prompt` or `response`.
    If you don't need the `conversational_history` information, simply respond to the prompt with your built-in knowledge.
                
    `conversational_history`:
        

        
""".trimIndent()

    fun callOllama(ollamaRequest: OllamaRequest): OllamaResponse {
        var historyEntryStringWithInstructions = PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS
        ollamaRequest.username?.let {
            val historyEntry = historyEntryRepository.findByUsername(it)
            if (historyEntry.isNotEmpty()) {
                val historyEntryString = historyEntry.joinToString("\n") { it.toString() }
                historyEntryStringWithInstructions += historyEntryString
            }
        }
        val systemMessage = SystemMessage(historyEntryStringWithInstructions)
        val currentPromptMessage = UserMessage(ollamaRequest.promptMessage)
        val prompt = Prompt(listOf(systemMessage, currentPromptMessage))
        val chatResponse = chatModel.call(prompt).result.output.content
        if (ollamaRequest.username != null) {
            historyEntryRepository.save(
                HistoryEntry(
                    promptMessage = ollamaRequest.promptMessage,
                    response= chatResponse,
                    username= ollamaRequest.username
                )
            )
        }
        return OllamaResponse(chatResponse)
    }
}
