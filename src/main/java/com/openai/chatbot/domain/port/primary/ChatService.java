package com.openai.chatbot.domain.port.primary;

import com.openai.chatbot.domain.port.secondary.chatcompletions.Chat;
import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatResponse;

import java.util.Collection;
import java.util.UUID;

public interface ChatService{

  //TODO revise needs (step 4)
  UUID startConversation( );

  ChatResponse addUserMessage( UUID chatId, String userMessage );

  Collection<Chat> getChatHistory( UUID chatId );

  void endConversation( UUID chatId );

}