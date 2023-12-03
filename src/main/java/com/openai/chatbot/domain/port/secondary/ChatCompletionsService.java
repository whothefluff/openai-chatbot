package com.openai.chatbot.domain.port.secondary;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;

/**
 * Handle chat completions
 */
public interface ChatCompletionsService{

  /**
   * Create a response for the given request
   * @param request the request for which to create a response
   * @return the response
   */
  ChatResponse createResponse( ChatRequest request );

}
