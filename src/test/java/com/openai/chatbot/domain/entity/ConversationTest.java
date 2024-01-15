package com.openai.chatbot.domain.entity;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashSet;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ConversationTest{

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
    val systemMessage = "Test System Message";
    val conversation = Conversation.initialStateBuilder( ).name( name ).systemMessage( systemMessage );
    val requestMsg = new ChatRequest.Message( ).role( system ).content( systemMessage );
    val request = new ChatRequest( ).addMessage( requestMsg );
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
    assertThatThrownBy( conversation::build ).isInstanceOf( IllegalArgumentException.class ).hasMessageContaining( "Name" );

  }

  @Test
  void build_EmptySystemMessage_ThrowsException( ){
    // Arrange
    val name = "Test Name";
    val conversation = Conversation.initialStateBuilder( ).name( name ).systemMessage( "   " );
    // Act & Assert
    assertThatThrownBy( conversation::build ).isInstanceOf( IllegalArgumentException.class ).hasMessageContaining( "System message" );

  }

  @Test
  void build_EmptyNameAndSystemMessage_ThrowsException( ){
    // Arrange
    val conversation = Conversation.initialStateBuilder( ).name( "   " ).systemMessage( "   " );
    // Act & Assert
    assertThatThrownBy( conversation::build ).isInstanceOf( IllegalArgumentException.class ).hasMessageContainingAll( "Name", "System message" );

  }

}
