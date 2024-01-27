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

  /**
   * Returns the requested chat
   * @param id the chat id
   * @return the chat
   * @throws ChatRepositoryException if the chat could not be retrieved
   */
  Conversation retrieveConversation( UUID id )
    throws ChatRepositoryException;

  /**
   * Returns all the chats
   * @return all the chats
   * @throws ChatRepositoryException if the chats could not be retrieved
   */
  Collection<Conversation> retrieveConversations( )
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