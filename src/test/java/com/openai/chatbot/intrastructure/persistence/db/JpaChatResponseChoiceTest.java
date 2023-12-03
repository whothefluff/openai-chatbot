package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoice;
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

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class JpaChatResponseChoiceTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameResponseAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new JpaChatResponseChoiceTest.FakeEqualChatResponse( );
    val choice1 = new JpaChatResponseChoice( ).response( response ).id( id );
    val choice2 = new JpaChatResponseChoice( ).response( response ).id( id );
    // Act & Assert
    assertThat( choice1 ).isEqualTo( choice2 );

  }

  @Test
  public void equals_differentResponseAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response1 = new JpaChatResponseChoiceTest.FakeDifferentChatResponse( );
    val choice1 = new JpaChatResponseChoice( ).response( response1 ).id( id );
    val response2 = new JpaChatResponse( );
    val choice2 = new JpaChatResponseChoice( ).response( response2 ).id( id );
    // Act & Assert
    assertThat( choice1 ).isNotEqualTo( choice2 );

  }

  @Test
  public void equals_sameResponseAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new JpaChatResponseChoiceTest.FakeEqualChatResponse( );
    val choice1 = new JpaChatResponseChoice( ).response( response ).id( id );
    val choice2 = new JpaChatResponseChoice( ).response( response ).id( id + 1 );
    // Act & Assert
    assertThat( choice1 ).isNotEqualTo( choice2 );

  }

  @Test
  public void hashCode_sameResponseAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val response = new JpaChatResponseChoiceTest.FakeChatResponse( );
    val choice1 = new JpaChatResponseChoice( ).response( response ).id( id );
    val choice2 = new JpaChatResponseChoice( ).response( response ).id( id );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertThat( choice1Hash ).isEqualTo( choice2Hash );

  }

  @Test
  public void hashCode_differentResponseAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response1 = new JpaChatResponseChoiceTest.FakeChatResponse( 1 );
    val choice1 = new JpaChatResponseChoice( ).response( response1 ).id( id );
    val response2 = new JpaChatResponseChoiceTest.FakeChatResponse( 2 );
    val choice2 = new JpaChatResponseChoice( ).response( response2 ).id( id );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertThat( choice1Hash ).isNotEqualTo( choice2Hash );

  }

  @Test
  public void hashCode_sameResponseAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val response = new JpaChatResponseChoiceTest.FakeChatResponse( );
    val choice1 = new JpaChatResponseChoice( ).response( response ).id( id );
    val choice2 = new JpaChatResponseChoice( ).response( response ).id( id + 1 );
    // Act
    val choice1Hash = choice1.hashCode( );
    val choice2Hash = choice2.hashCode( );
    // Assert
    assertThat( choice1Hash ).isNotEqualTo( choice2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new JpaChat( );
    val response = new JpaChatResponse( );
    chat.addResponse( response );
    val choice = new JpaChatResponseChoice( );
    response.addChoice( choice );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( response.id( ) ).isPositive( );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val response = new JpaChatResponse( );
    chat.addResponse( response );
    val choice = new JpaChatResponseChoice( );
    response.addChoice( choice );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( choice.createdAt( ), Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new JpaChat( );
    val response = new JpaChatResponse( );
    chat.addResponse( response );
    val choice = new JpaChatResponseChoice( ).index( 1 );
    response.addChoice( choice );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( choice );
    val initialValue = choice.createdAt( );
    // Act
    choice.index( 2 );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = choice.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val response = new JpaChatResponse( );
    chat.addResponse( response );
    val choice = new JpaChatResponseChoice( ).index( 1 );
    response.addChoice( choice );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( choice );
    // Act
    choice.index( 2 );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = choice.createdAt( );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChatResponse extends JpaChatResponse{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChatResponse extends JpaChatResponseChoiceTest.FakeChatResponse{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatResponse extends JpaChatResponseChoiceTest.FakeChatResponse{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}