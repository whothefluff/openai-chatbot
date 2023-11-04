package com.openai.chatbot.domain.port.secondary.chatcompletions;

import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;

public interface ChatRequest{

  Integer id( );

  Instant createdAt( );

  Instant modifiedAt( );

  String model( );

  String functionCall( );

  BigDecimal temperature( );

  BigDecimal topP( );

  Integer n( );

  Boolean stream( );

  Integer maxTokens( );

  BigDecimal presencePenalty( );

  BigDecimal frequencyPenalty( );

  String logitBias( );

  String user( );

  ChatResponse previousResponse( );

  Collection<FunctionDefinition> functionDefinitions( );

  Collection<ChatRequest.Message> messages( );

  interface FunctionDefinition{

    Integer id( );

    Instant createdAt( );

    Instant modifiedAt( );

    @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
              message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) String name( );

    String description( );

    String parameters( );

  }

  interface Message{

    Integer id( );

    Instant createdAt( );

    Instant modifiedAt( );

    ChatMessageRole role( );

    String content( );

    @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
              message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) String name( );

    ChatRequest.Message.FunctionCall functionCall( );

    interface FunctionCall{

      @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
                message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) String name( );

      String arguments( );

    }

  }

}
