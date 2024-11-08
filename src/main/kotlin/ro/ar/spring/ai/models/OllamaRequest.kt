package ro.ar.spring.ai.models

import com.fasterxml.jackson.annotation.JsonProperty

class OllamaRequest(
    @JsonProperty("prompt_message") val promptMessage: String,
    @JsonProperty("username") val username: String?
)