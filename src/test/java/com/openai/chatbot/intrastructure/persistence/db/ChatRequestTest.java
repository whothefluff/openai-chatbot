package com.openai.chatbot.intrastructure.persistence.db;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
public class ChatRequestTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameChatAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat ).id( id );
    val request2 = new ChatRequest( ).chat( chat ).id( id );
    // Act & Assert
    assertEquals( request1, request2 );

  }

  @Test
  public void equals_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat1 ).id( id );
    val chat2 = new Chat( ).id( UUID.randomUUID( ) );
    val request2 = new ChatRequest( ).chat( chat2 ).id( id );
    // Act & Assert
    assertNotEquals( request1, request2 );

  }

  @Test
  public void equals_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat ).id( id );
    val request2 = new ChatRequest( ).chat( chat ).id( id + 1 );
    // Act & Assert
    assertNotEquals( request1, request2 );

  }

  @Test
  public void hashCode_sameChatAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val chat = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat ).id( id );
    val request2 = new ChatRequest( ).chat( chat ).id( id );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertEquals( request1Hash, request2Hash );

  }

  @Test
  public void hashCode_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat1 ).id( id );
    val chat2 = new Chat( ).id( UUID.randomUUID( ) );
    val request2 = new ChatRequest( ).chat( chat2 ).id( id );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertNotEquals( request1Hash, request2Hash );

  }

  @Test
  public void hashCode_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new Chat( ).id( UUID.randomUUID( ) );
    val request1 = new ChatRequest( ).chat( chat ).id( id );
    val request2 = new ChatRequest( ).chat( chat ).id( id + 1 );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertNotEquals( request1Hash, request2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertNotNull( request.id( ) );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( request.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "initial" );
    chat.addRequest( request );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( request );
    val initialValue = request.createdAt( );
    // Act
    request.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = request.createdAt( );
    // Assert
    assertEquals( initialValue, valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "initial" );
    chat.addRequest( request );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( request );
    // Act
    request.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = request.createdAt( );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) < maxAllowedDifference );

  }

}