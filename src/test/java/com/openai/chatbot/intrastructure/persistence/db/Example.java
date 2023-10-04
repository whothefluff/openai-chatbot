package com.openai.chatbot.intrastructure.persistence.db;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class Example{

  private Chat chat;

  @BeforeEach
  public void setUp( ){

    this.chat = new Chat( );

  }

  @Test
  public void testId( ){

    UUID id = UUID.randomUUID( );
    this.chat.id( id );
    Assertions.assertEquals( id, this.chat.id( ) );

  }

  @Test
  public void testCreatedAt( ){

    Instant createdAt = Instant.now( );
    this.chat.createdAt( createdAt );
    Assertions.assertEquals( createdAt, this.chat.createdAt( ) );

  }

  @Test
  public void testModifiedAt( ){

    Instant modifiedAt = Instant.now( );
    this.chat.modifiedAt( modifiedAt );
    Assertions.assertEquals( modifiedAt, this.chat.modifiedAt( ) );

  }

  @Test
  public void testName( ){

    String name = "Test Chat";
    this.chat.name( name );
    Assertions.assertEquals( name, this.chat.name( ) );

  }

  @Test
  public void testRequests( ){

    Set<ChatRequest> requests = new LinkedHashSet<>( );
    this.chat.requests( requests );
    Assertions.assertEquals( requests, this.chat.requests( ) );

  }

  @Test
  public void testResponses( ){

    Set<ChatResponse> responses = new LinkedHashSet<>( );
    this.chat.responses( responses );
    Assertions.assertEquals( responses, this.chat.responses( ) );
    
  }

}
