package com.monal.driveEase.ai;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.monal.driveEase.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class AiClient {

    @Value("${ai.base-url:https://api.groq.com/openai/v1}")
    private String baseUrl;

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.model:llama-3.3-70b-versatile}")
    private String model;

    private final RestClient restClient = RestClient.create();

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Message(String content) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Choice(Message message) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    record ChatResponse(List<Choice> choices) {}


    public Map<String, Object> askJson(String system, String user) {

        boolean local = baseUrl.startsWith("http://localhost") || baseUrl.startsWith("http://127.0.0.1");
        boolean hasKey = apiKey != null && !apiKey.isBlank();

        if (!local && !hasKey) {
            log.error("AI_API_KEY is not set");
            throw new BadRequestException("AI features are not configured right now.");
        }

        String url = (baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl)
                + "/chat/completions";

        Map<String, Object> body = Map.of(
                "model", model,
                "temperature", 0,
                "messages", List.of(
                        Map.of("role", "system", "content", system),
                        Map.of("role", "user", "content", user)),
                "response_format", Map.of("type", "json_object")
        );

        try {
            RestClient.RequestBodySpec request = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON);

            if (hasKey) {
                request = request.header("Authorization", "Bearer " + apiKey);
            }

            ChatResponse response = request.body(body).retrieve().body(ChatResponse.class);

            if (response != null && response.choices() != null && !response.choices().isEmpty()
                    && response.choices().get(0).message() != null) {

                String content = response.choices().get(0).message().content();


                int start = content == null ? -1 : content.indexOf('{');
                int end = content == null ? -1 : content.lastIndexOf('}');

                if (start >= 0 && end > start) {
                    return JsonParserFactory.getJsonParser().parseMap(content.substring(start, end + 1));
                }
            }
            log.error("AI returned no usable JSON: {}", response);

        } catch (RuntimeException e) {
            log.error("AI call failed", e);
        }

        throw new BadRequestException("The AI service is temporarily unavailable. Please try again.");
    }
}