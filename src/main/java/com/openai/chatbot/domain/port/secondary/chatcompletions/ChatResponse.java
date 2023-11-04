package com.openai.chatbot.domain.port.secondary.chatcompletions;

import java.time.Instant;
import java.util.Collection;

public interface ChatResponse{

  Integer id( );

  Instant createdAt( );

  Instant modifiedAt( );

  String object( );

  Integer created( );

  String model( );

  Collection<Choice> choices( );

  ChatResponse.Usage usage( );

  interface Choice{

    Integer id( );

    Instant createdAt( );

    Instant modifiedAt( );

    Integer index( );

    String finishReason( );

    ChatResponse.Choice.Message message( );

    interface Message{

      ChatMessageRole role( );

      String content( );

      ChatResponse.Choice.Message.FunctionCall functionCall( );

      interface FunctionCall{

        String name( );

        String arguments( );

      }

    }

  }

  interface Usage{

    Integer promptTokens( );

    Integer completionTokens( );

    Integer totalTokens( );

  }

}