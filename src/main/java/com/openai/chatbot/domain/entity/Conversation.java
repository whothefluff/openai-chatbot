package com.openai.chatbot.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;

/**
 * A conversation
 */
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
public class Conversation{

  UUID id;
  Instant createdAt;
  Instant modifiedAt;
  String name;
  Collection<ChatRequest> requests = new LinkedHashSet<>( );
  Collection<ChatResponse> responses = new LinkedHashSet<>( );

  public Conversation addRequest( ChatRequest request ){

    log.entry( request );
    this.requests.add( request );
    return log.exit( this );
    
  }

}