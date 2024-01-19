package com.openai.chatbot.domain.port.primary;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatServiceException;

import java.util.Collection;
import java.util.UUID;

/**
 * Chat related operations
 */
public interface ChatService{

  /**
   * @param name          works as natural id
   * @param systemMessage sets the tone of the conversation
   * @return the created conversation
   * @throws ChatServiceException if the conversation could not be created
   */
  Conversation startConversation( String name, String systemMessage )
    throws ChatServiceException;

  Collection<Conversation> getConversations( );

  Conversation getConversation( UUID id )
    throws ChatServiceException;

  Conversation updateConversation( UUID id, Conversation conversation )
    throws ChatServiceException;

  /**
   * @param id the conversation id
   * @throws ChatServiceException if the conversation could not be deleted
   */
  void deleteConversation( UUID id )
    throws ChatServiceException;

  ChatResponse addUserMessage( UUID chatId, ChatRequest userMessage );

  Collection<?> getConversationMessages( UUID chatId ); //TODO revise

  void deleteBotMessage( UUID chatId, Integer messageId );

  void deleteUserMessage( UUID chatId, Integer messageId );

}