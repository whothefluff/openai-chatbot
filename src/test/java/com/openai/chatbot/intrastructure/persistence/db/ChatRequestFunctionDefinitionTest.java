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
class ChatRequestFunctionDefinitionTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameRequestAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestFunctionDefinitionTest.FakeEqualChatRequest( );
    val function1 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    // Act & Assert
    assertEquals( function1, function2 );

  }

  @Test
  public void equals_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new ChatRequestFunctionDefinitionTest.FakeDifferentChatRequest( );
    val function1 = new ChatRequestFunctionDefinition( ).request( request1 ).id( id );
    val request2 = new ChatRequest( );
    val function2 = new ChatRequestFunctionDefinition( ).request( request2 ).id( id );
    // Act & Assert
    assertNotEquals( function1, function2 );

  }

  @Test
  public void equals_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestFunctionDefinitionTest.FakeEqualChatRequest( );
    val function1 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new ChatRequestFunctionDefinition( ).request( request ).id( id + 1 );
    // Act & Assert
    assertNotEquals( function1, function2 );

  }

  @Test
  public void hashCode_sameRequestAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val request = new ChatRequestFunctionDefinitionTest.FakeChatRequest( );
    val function1 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertEquals( function1Hash, function2Hash );

  }

  @Test
  public void hashCode_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new ChatRequestFunctionDefinitionTest.FakeChatRequest( 1 );
    val function1 = new ChatRequestFunctionDefinition( ).request( request1 ).id( id );
    val request2 = new ChatRequestFunctionDefinitionTest.FakeChatRequest( 2 );
    val function2 = new ChatRequestFunctionDefinition( ).request( request2 ).id( id );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertNotEquals( function1Hash, function2Hash );

  }

  @Test
  public void hashCode_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new ChatRequestFunctionDefinitionTest.FakeChatRequest( );
    val function1 = new ChatRequestFunctionDefinition( ).request( request ).id( id );
    val function2 = new ChatRequestFunctionDefinition( ).request( request ).id( id + 1 );
    // Act
    val function1Hash = function1.hashCode( );
    val function2Hash = function2.hashCode( );
    // Assert
    assertNotEquals( function1Hash, function2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    val request = new ChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val function = new ChatRequestFunctionDefinition( ).name( "some_name" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
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
    val function = new ChatRequestFunctionDefinition( ).name( "some_name" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
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
    val function = new ChatRequestFunctionDefinition( ).name( "initial" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( function );
    val initialValue = function.createdAt( );
    // Act
    function.name( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = function.createdAt( );
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
    val function = new ChatRequestFunctionDefinition( ).name( "initial" ).parameters( "{ some parameters }" );
    request.addFunctionDefinition( function );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( function );
    // Act
    function.name( "updated" );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = function.createdAt( );
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
  private static class FakeEqualChatRequest extends ChatRequestFunctionDefinitionTest.FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatRequest extends ChatRequestFunctionDefinitionTest.FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}