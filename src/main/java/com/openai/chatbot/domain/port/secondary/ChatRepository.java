package com.openai.chatbot.domain.port.secondary;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;

import java.util.Collection;
import java.util.UUID;

/**
 * A way to access information of the chats
 */
public interface ChatRepository{

  /**
   * Saves a new chat with a system message
   * @param chat the chat to save
   * @return the saved chat
   * @throws ChatRepositoryException if there is an error saving the chat
   */
  Conversation saveNewConversation( Conversation chat )
    throws ChatRepositoryException;

  Conversation retrieveConversation( UUID id )
    throws ChatRepositoryException;

  Conversation updateConversation( Conversation chat )
    throws ChatRepositoryException;

  /**
   * @param id the chat id
   * @throws ChatRepositoryException if the chat could not be deleted
   */
  void deleteConversation( UUID id )
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