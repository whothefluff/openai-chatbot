package com.openai.chatbot.domain.service;

import com.openai.chatbot.domain.port.primary.ChatService;
import com.openai.chatbot.domain.port.secondary.chatcompletions.Chat;
import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatCompletionsService;
import com.openai.chatbot.domain.port.secondary.chatcompletions.ChatResponse;
import com.openai.chatbot.domain.port.secondary.data.JpaChatRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@Service
public class ChatServiceImpl implements ChatService{

  final ChatCompletionsService openAIService;
  final JpaChatRepository chatRepository;

  @Override
  public UUID startConversation( ){

    return null;
  }

  @Override
  public ChatResponse addUserMessage( final UUID chatId, final String userMessage ){

    return null;
  }

  @Override
  public Collection<Chat> getChatHistory( final UUID chatId ){

    return null;
  }

  @Override
  public void endConversation( final UUID chatId ){

  }

}