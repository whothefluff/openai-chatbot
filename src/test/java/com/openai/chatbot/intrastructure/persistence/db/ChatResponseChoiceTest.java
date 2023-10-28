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

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class ChatResponseChoiceTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameResponseAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new ChatResponseChoiceTest.FakeEqualChatResponse( );
    val choice1 = new ChatResponseChoice( ).response( response ).id( id );
    val choice2 = new ChatResponseChoice( ).response( response ).id( id );
    // Act & Assert
    assertEquals( choice1, choice2 );

  }

  @Test
  public void equals_differentResponseAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response1 = new ChatResponseChoiceTest.FakeDifferentChatResponse( );
    val choice1 = new ChatResponseChoice( ).response( response1 ).id( id );
    val response2 = new ChatResponse( );
    val choice2 = new ChatResponseChoice( ).response( response2 ).id( id );
    // Act & Assert
    assertNotEquals( choice1, choice2 );

  }

  @Test
  public void equals_sameResponseAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new ChatResponseChoiceTest.FakeEqualChatResponse( );
    val choice1 = new ChatResponseChoice( ).response( response ).id( id );
    val choice2 = new ChatResponseChoice( ).response( response ).id( id + 1 );
    // Act & Assert
    assertNotEquals( choice1, choice2 );

  }

  @Test
  public void hashCode_sameResponseAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val response = new ChatResponseChoiceTest.FakeChatResponse( );
    val choice1 = new ChatResponseChoice( ).response( response ).id( id );
    val choice2 = new ChatResponseChoice( ).response( response ).id( id );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertEquals( choice1Hash, choice2Hash );

  }

  @Test
  public void hashCode_differentResponseAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response1 = new ChatResponseChoiceTest.FakeChatResponse( 1 );
    val choice1 = new ChatResponseChoice( ).response( response1 ).id( id );
    val response2 = new ChatResponseChoiceTest.FakeChatResponse( 2 );
    val choice2 = new ChatResponseChoice( ).response( response2 ).id( id );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertNotEquals( choice1Hash, choice2Hash );

  }

  @Test
  public void hashCode_sameResponseAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new ChatResponseChoiceTest.FakeChatResponse( );
    val choice1 = new ChatResponseChoice( ).response( response ).id( id );
    val choice2 = new ChatResponseChoice( ).response( response ).id( id + 1 );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertNotEquals( choice1Hash, choice2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    val response = new ChatResponse( );
    chat.addResponse( response );
    val choice = new ChatResponseChoice( );
    response.addChoice( choice );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertNotNull( response.id( ) );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val response = new ChatResponse( );
    chat.addResponse( response );
    val choice = new ChatResponseChoice( );
    response.addChoice( choice );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( response.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new Chat( );
    val response = new ChatResponse( );
    chat.addResponse( response );
    val choice = new ChatResponseChoice( ).index( 1 );
    response.addChoice( choice );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( choice );
    val initialValue = choice.createdAt( );
    // Act
    choice.index( 2 );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = choice.createdAt( );
    // Assert
    assertEquals( initialValue, valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    val response = new ChatResponse( );
    chat.addResponse( response );
    val choice = new ChatResponseChoice( ).index( 1 );
    response.addChoice( choice );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( choice );
    // Act
    choice.index( 2 );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = choice.createdAt( );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) < maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChatResponse extends ChatResponse{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChatResponse extends ChatResponseChoiceTest.FakeChatResponse{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatResponse extends ChatResponseChoiceTest.FakeChatResponse{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}