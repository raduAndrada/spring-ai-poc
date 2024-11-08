package ro.ar.spring.ai.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.ar.spring.ai.models.OllamaRequest
import ro.ar.spring.ai.models.OllamaResponse
import ro.ar.spring.ai.service.OllamaChatbotService

@RestController
@RequestMapping("/ollama")
class OllamaController (
    @Autowired val ollamaChatbotService: OllamaChatbotService
){
    
    @PostMapping("/chat")
    fun chat(@RequestBody ollamaRequest: OllamaRequest) : OllamaResponse {
        return ollamaChatbotService.callOllama(ollamaRequest)
    }

}