package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.common.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class ChatRequestMessageTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameChatAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestMessageTest.FakeEqualChatRequest( );
    val message1 = new ChatRequestMessage( ).request( request ).id( id );
    val message2 = new ChatRequestMessage( ).request( request ).id( id );
    // Act & Assert
    assertEquals( message1, message2 );

  }

  @Test
  public void equals_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new ChatRequestMessageTest.FakeDifferentChatRequest( );
    val message1 = new ChatRequestMessage( ).request( request1 ).id( id );
    val request2 = new ChatRequest( );
    val message2 = new ChatRequestMessage( ).request( request2 ).id( id );
    // Act & Assert
    assertNotEquals( message1, message2 );

  }

  @Test
  public void equals_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestMessageTest.FakeEqualChatRequest( );
    val message1 = new ChatRequestMessage( ).request( request ).id( id );
    val message2 = new ChatRequestMessage( ).request( request ).id( id + 1 );
    // Act & Assert
    assertNotEquals( message1, message2 );

  }

  @Test
  public void hashCode_sameChatAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val request = new ChatRequestMessageTest.FakeChatRequest( );
    val message1 = new ChatRequestMessage( ).request( request ).id( id );
    val message2 = new ChatRequestMessage( ).request( request ).id( id );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertEquals( message1Hash, message2Hash );

  }

  @Test
  public void hashCode_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new ChatRequestMessageTest.FakeChatRequest( 1 );
    val message1 = new ChatRequestMessage( ).request( request1 ).id( id );
    val request2 = new ChatRequestMessageTest.FakeChatRequest( 2 );
    val message2 = new ChatRequestMessage( ).request( request2 ).id( id );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertNotEquals( message1Hash, message2Hash );

  }

  @Test
  public void hashCode_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestMessageTest.FakeChatRequest( );
    val message1 = new ChatRequestMessage( ).request( request ).id( id );
    val message2 = new ChatRequestMessage( ).request( request ).id( id + 1 );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertNotEquals( message1Hash, message2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new ChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
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
    val message = new ChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( request.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new ChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( message );
    val initialValue = message.createdAt( );
    // Act
    message.role( Role.system );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = message.createdAt( );
    // Assert
    assertEquals( initialValue, valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new ChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( message );
    // Act
    message.role( Role.system );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = message.createdAt( );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) < maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChatRequest extends ChatRequest{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChatRequest extends FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatRequest extends FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}