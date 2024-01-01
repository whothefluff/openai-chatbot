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

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@DataJpaTest
class JpaChatRequestFunctionDefinitionTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameRequestAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestFunctionDefinitionTest.FakeEqualChatRequest( );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    // Act & Assert
    assertThat( function1 ).isEqualTo( function2 );

  }

  @Test
  public void equals_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new JpaChatRequestFunctionDefinitionTest.FakeDifferentChatRequest( );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request1 ).id( id );
    val request2 = new JpaChatRequest( );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request2 ).id( id );
    // Act & Assert
    assertThat( function1 ).isNotEqualTo( function2 );

  }

  @Test
  public void equals_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestFunctionDefinitionTest.FakeEqualChatRequest( );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id + 1 );
    // Act & Assert
    assertThat( function1 ).isNotEqualTo( function2 );

  }

  @Test
  public void hashCode_sameRequestAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val request = new JpaChatRequestFunctionDefinitionTest.FakeChatRequest( );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertThat( function1Hash ).isEqualTo( function2Hash );

  }

  @Test
  public void hashCode_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new JpaChatRequestFunctionDefinitionTest.FakeChatRequest( 1 );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request1 ).id( id );
    val request2 = new JpaChatRequestFunctionDefinitionTest.FakeChatRequest( 2 );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request2 ).id( id );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertThat( function1Hash ).isNotEqualTo( function2Hash );

  }

  @Test
  public void hashCode_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestFunctionDefinitionTest.FakeChatRequest( );
    val function1 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new JpaChatRequestFunctionDefinition( ).request( request ).id( id + 1 );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertThat( function1Hash ).isNotEqualTo( function2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val function = new JpaChatRequestFunctionDefinition( ).name( "some_name" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
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
    val function = new JpaChatRequestFunctionDefinition( ).name( "some_name" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( function.createdAt( ), Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val function = new JpaChatRequestFunctionDefinition( ).name( "initial" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( function );
    val initialValue = function.createdAt( );
    // Act
    function.name( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = function.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val function = new JpaChatRequestFunctionDefinition( ).name( "initial" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( function );
    // Act
    function.name( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = function.createdAt( );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChatRequest extends JpaChatRequest{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChatRequest extends JpaChatRequestFunctionDefinitionTest.FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatRequest extends JpaChatRequestFunctionDefinitionTest.FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}