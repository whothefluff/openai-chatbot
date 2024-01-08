package com.openai.chatbot.domain.entity;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Asks for a model response for the given chat conversation
 */
@SuppressWarnings( { "ClassWithTooManyFields", "UseOfConcreteClass" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
public class ChatRequest{

  Integer id;
  Instant createdAt;
  Instant modifiedAt;
  String model;
  String functionCall;
  BigDecimal temperature;
  BigDecimal topP;
  Integer n;
  Boolean stream;
  Integer maxTokens;
  BigDecimal presencePenalty;
  BigDecimal frequencyPenalty;
  String logitBias;
  String user;
  ChatResponse previousResponse;
  Collection<ChatRequest.FunctionDefinition> functionDefinitions = new LinkedHashSet<>( );
  Collection<ChatRequest.Message> messages = new LinkedHashSet<>( );

  public ChatRequest addMessage( ChatRequest.Message message ){

    log.entry( message );
    this.messages.add( message );
    return log.exit( this );

  }

  public ChatRequest functionDefinition( ChatRequest.FunctionDefinition functionDefinition ){

    log.entry( functionDefinition );
    this.functionDefinitions.add( functionDefinition );
    return log.exit( this );

  }

  /**
   * A function the model may generate JSON inputs for
   */
  @Data
  @EqualsAndHashCode
  @ToString
  @FieldDefaults( level = AccessLevel.PROTECTED )
  @Accessors( chain = true,
              fluent = true )
  public static class FunctionDefinition{

    Integer id;
    Instant createdAt;
    Instant modifiedAt;
    @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$", // NON-NLS @formatter:off
              message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) //NON-NLS
    String name;
    String description;
    String parameters;

  }

  /**
   * One of messages comprising the conversation so far
   */
  @Data
  @EqualsAndHashCode
  @ToString
  @FieldDefaults( level = AccessLevel.PROTECTED )
  @Accessors( chain = true,
              fluent = true )
  public static class Message{

    Integer id;
    Instant createdAt;
    Instant modifiedAt;
    ChatMessageRole role;
    String content;
    @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$", // NON-NLS @formatter:off
              message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) //NON-NLS
    String name;
    FunctionCall functionCall;

    /**
     * The name and arguments of a function that should be called, as generated by the model
     */
    @Data
    @EqualsAndHashCode
    @ToString
    @FieldDefaults( level = AccessLevel.PROTECTED )
    @Accessors( chain = true,
                fluent = true )
    public static class FunctionCall{

      @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$", // NON-NLS @formatter:off
                message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." ) //NON-NLS
      String name;
      String arguments;

    }

  }

}
