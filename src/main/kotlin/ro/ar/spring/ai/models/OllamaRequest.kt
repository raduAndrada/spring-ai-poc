package ro.ar.spring.ai.models

import com.fasterxml.jackson.annotation.JsonProperty

class OllamaRequest(
    @JsonProperty("pompt_message") val promptMessage: String,
    @JsonProperty("username") val username: String?
)