package com.openai.chatbot.domain.port.secondary;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;

import java.util.Collection;

/**
 * A way to access information of the chats
 */
public interface ChatRepository{

  /**
   * Saves a new chat with a system message
   * @param chat        the chat to save
   * @return the saved chat
   * @throws ChatRepositoryException if there is an error saving the chat
   */
  Conversation saveNewConversation( Conversation chat )
    throws ChatRepositoryException;

  void deleteConversation( Conversation chat )
    throws ChatRepositoryException;

  Conversation updateConversation( Conversation chat )
    throws ChatRepositoryException;

  Conversation addRequestToChat( Conversation chat, ChatRequest chatRequest )
    throws ChatRepositoryException;

  Conversation addResponseToChat( Conversation chat, ChatResponse chatResponse )
    throws ChatRepositoryException;

  void deleteResponse( ChatResponse chatResponse )
    throws ChatRepositoryException;

  void deleteRequest( ChatRequest chatRequest )
    throws ChatRepositoryException;

  Collection<Conversation> searchChatByContent( String content )
    throws ChatRepositoryException;

}