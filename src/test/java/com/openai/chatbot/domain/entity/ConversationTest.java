package com.openai.chatbot.domain.entity;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ConversationTest{

  @Test
  void addRequest_WithValidRequest_AddsRequestOnce( ){
    // Arrange
    val convWithNoRequests = new Conversation( ).requests( new LinkedHashSet<>( 1 ) );
    val request = new ChatRequest( );
    // Act
    convWithNoRequests.addRequest( request );
    // Assert
    assertThat( convWithNoRequests.requests( ) ).containsOnlyOnce( request );

  }

  @Test
  void addResponse_WithValidResponse_AddsResponseOnce( ){
    // Arrange
    val convWithNoResponses = new Conversation( ).responses( new LinkedHashSet<>( 1 ) );
    val response = new ChatResponse( );
    // Act
    convWithNoResponses.addResponse( response );
    // Assert
    assertThat( convWithNoResponses.responses( ) ).containsOnlyOnce( response );

  }

  @Test
  void initialStateBuilder_WhenCalled_ReturnsNonNull( ){
    // Act
    val builder = Conversation.initialStateBuilder( );
    // Assert
    assertThat( builder ).isNotNull( );

  }

  @Test
  void build_ValidInputs_ReturnsConversation( ){
    // Arrange
    val name = "Test Name";
    val model = "Test Model";
    val systemMessage = "Test System Message";
    val conversation = Conversation.initialStateBuilder( ).name( name ).model( model ).systemMessage( systemMessage );
    val requestMsg = new ChatRequest.Message( ).role( system ).content( systemMessage );
    val request = new ChatRequest( ).model( model ).addMessage( requestMsg );
    val expectedConversation = new Conversation( ).name( name ).addRequest( request );
    // Act
    val result = conversation.build( );
    // Assert
    assertThat( result ).isEqualTo( expectedConversation );

  }

  @Test
  void build_EmptyName_ThrowsException( ){
    // Arrange
    val systemMessage = "Test System Message";
    val conversation = Conversation.initialStateBuilder( ).name( "   " ).systemMessage( systemMessage );
    // Act & Assert
    assertThatExceptionOfType( IllegalArgumentException.class ).isThrownBy( conversation::build ).withMessageContaining( "Name" );

  }

  @Test
  void build_EmptySystemMessage_ThrowsException( ){
    // Arrange
    val name = "Test Name";
    val conversation = Conversation.initialStateBuilder( ).name( name ).systemMessage( "   " );
    // Act & Assert
    assertThatExceptionOfType( IllegalArgumentException.class ).isThrownBy( conversation::build ).withMessageContaining( "System message" );

  }

  @Test
  void build_EmptyModel_ThrowsException( ){
    // Arrange
    val name = "Test Name";
    val conversation = Conversation.initialStateBuilder( ).name( name ).model( "   " );
    // Act & Assert
    assertThatExceptionOfType( IllegalArgumentException.class ).isThrownBy( conversation::build ).withMessageContaining( "Model" );

  }

  @Test
  void build_EmptyNameAndModelAndSystemMessage_ThrowsException( ){
    // Arrange
    val conversation = Conversation.initialStateBuilder( ).name( "   " ).systemMessage( "   " );
    // Act & Assert
    assertThatExceptionOfType( IllegalArgumentException.class ).isThrownBy( conversation::build )
                                                               .withMessageContainingAll( "Name", "Model", "System message" );

  }

}
