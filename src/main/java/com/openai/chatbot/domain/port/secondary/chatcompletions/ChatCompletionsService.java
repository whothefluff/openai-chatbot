package com.openai.chatbot.domain.port.secondary.chatcompletions;

public interface ChatCompletionsService{

  ChatResponse createResponse( ChatRequest request );

}
