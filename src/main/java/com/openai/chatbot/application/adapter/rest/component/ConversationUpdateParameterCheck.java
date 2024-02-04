package com.openai.chatbot.application.adapter.rest.component;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;

import java.util.UUID;

/**
 * Validates the parameters for the update conversation operation
 */
public interface ConversationUpdateParameterCheck{

  /**
   * Throws an exception if the parameters are not valid
   * @param id               the conversation id
   * @param conversationBody the conversation body
   */
  void validate( UUID id, ConversationBody conversationBody );

}
