package com.openai.chatbot.intrastructure.persistence.db;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class ChatResponseTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameChatAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new ChatResponseTest.FakeEqualChat( );
    val response1 = new ChatResponse( ).chat( chat ).id( id );
    val response2 = new ChatResponse( ).chat( chat ).id( id );
    // Act & Assert
    assertThat( response1 ).isEqualTo( response2 );

  }

  @Test
  public void equals_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new ChatResponseTest.FakeDifferentChat( );
    val response1 = new ChatResponse( ).chat( chat1 ).id( id );
    val chat2 = new Chat( );
    val response2 = new ChatResponse( ).chat( chat2 ).id( id );
    // Act & Assert
    assertThat( response1 ).isNotEqualTo( response2 );

  }

  @Test
  public void equals_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new ChatResponseTest.FakeEqualChat( );
    val response1 = new ChatResponse( ).chat( chat ).id( id );
    val response2 = new ChatResponse( ).chat( chat ).id( id + 1 );
    // Act & Assert
    assertThat( response1 ).isNotEqualTo( response2 );

  }

  @Test
  public void hashCode_sameChatAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val chat = new ChatResponseTest.FakeChat( );
    val response1 = new ChatResponse( ).chat( chat ).id( id );
    val response2 = new ChatResponse( ).chat( chat ).id( id );
    // Act
    val response1Hash = response1.hashCode( );
    val response2Hash = response2.hashCode( );
    // Assert
    assertThat( response1Hash ).isEqualTo( response2Hash );

  }

  @Test
  public void hashCode_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new ChatResponseTest.FakeChat( 1 );
    val response1 = new ChatResponse( ).chat( chat1 ).id( id );
    val chat2 = new ChatResponseTest.FakeChat( 2 );
    val response2 = new ChatResponse( ).chat( chat2 ).id( id );
    // Act
    val response1Hash = response1.hashCode( );
    val response2Hash = response2.hashCode( );
    // Assert
    assertThat( response1Hash ).isNotEqualTo( response2Hash );

  }

  @Test
  public void hashCode_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new ChatResponseTest.FakeChat( );
    val response1 = new ChatResponse( ).chat( chat ).id( id );
    val response2 = new ChatResponse( ).chat( chat ).id( id + 1 );
    // Act
    val response1Hash = response1.hashCode( );
    val response2Hash = response2.hashCode( );
    // Assert
    assertThat( response1Hash ).isNotEqualTo( response2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    val response = new ChatResponse( ).model( "some model" );
    chat.addResponse( response );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( response.id( ) ).isPositive( );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val response = new ChatResponse( ).model( "some model" );
    chat.addResponse( response );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( response.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new Chat( );
    val response = new ChatResponse( ).model( "initial" );
    chat.addResponse( response );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( response );
    val initialValue = response.createdAt( );
    // Act
    response.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = response.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val response = new ChatResponse( ).model( "initial" );
    chat.addResponse( response );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( response );
    // Act
    response.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = response.createdAt( );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) < maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChat extends Chat{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChat extends ChatResponseTest.FakeChat{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChat extends ChatResponseTest.FakeChat{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}