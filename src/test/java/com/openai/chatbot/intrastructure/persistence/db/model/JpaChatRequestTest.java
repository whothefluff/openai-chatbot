package com.openai.chatbot.intrastructure.persistence.db.model;

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

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@DataJpaTest
class JpaChatRequestTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameChatAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new FakeEqualChat( );
    val request1 = new JpaChatRequest( ).chat( chat ).id( id );
    val request2 = new JpaChatRequest( ).chat( chat ).id( id );
    // Act & Assert
    assertThat( request1 ).isEqualTo( request2 );

  }

  @Test
  public void equals_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new FakeDifferentChat( );
    val request1 = new JpaChatRequest( ).chat( chat1 ).id( id );
    val chat2 = new JpaChat( );
    val request2 = new JpaChatRequest( ).chat( chat2 ).id( id );
    // Act & Assert
    assertThat( request1 ).isNotEqualTo( request2 );

  }

  @Test
  public void equals_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new FakeEqualChat( );
    val request1 = new JpaChatRequest( ).chat( chat ).id( id );
    val request2 = new JpaChatRequest( ).chat( chat ).id( id + 1 );
    // Act & Assert
    assertThat( request1 ).isNotEqualTo( request2 );

  }

  @Test
  public void hashCode_sameChatAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val chat = new FakeChat( );
    val request1 = new JpaChatRequest( ).chat( chat ).id( id );
    val request2 = new JpaChatRequest( ).chat( chat ).id( id );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertThat( request1Hash ).isEqualTo( request2Hash );

  }

  @Test
  public void hashCode_differentChatAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat1 = new FakeChat( 1 );
    val request1 = new JpaChatRequest( ).chat( chat1 ).id( id );
    val chat2 = new FakeChat( 2 );
    val request2 = new JpaChatRequest( ).chat( chat2 ).id( id );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertThat( request1Hash ).isNotEqualTo( request2Hash );

  }

  @Test
  public void hashCode_sameChatAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val chat = new FakeChat( );
    val request1 = new JpaChatRequest( ).chat( chat ).id( id );
    val request2 = new JpaChatRequest( ).chat( chat ).id( id + 1 );
    // Act
    val request1Hash = request1.hashCode( );
    val request2Hash = request2.hashCode( );
    // Assert
    assertThat( request1Hash ).isNotEqualTo( request2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( request.id( ) ).isPositive( );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( request.createdAt( ), Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "initial" );
    chat.addRequest( request );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( request );
    val initialValue = request.createdAt( );
    // Act
    request.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = request.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "initial" );
    chat.addRequest( request );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( request );
    // Act
    request.model( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = request.createdAt( );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChat extends JpaChat{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChat extends FakeChat{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChat extends FakeChat{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}