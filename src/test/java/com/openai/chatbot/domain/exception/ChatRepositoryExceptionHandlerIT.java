package com.openai.chatbot.domain.exception;

import com.openai.chatbot.domain.port.primary.ChatService;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class ChatRepositoryExceptionHandlerIT{

  @SpyBean
  private ChatRepository repository;
  @Autowired
  private ChatService service;

  @Test
  void startConversation_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).saveNewConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void startConversation_ChatRepositoryNotFoundExceptionThrown_ThrowsAsChatServiceNotFoundException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.NotFound( ) ).when( this.repository ).saveNewConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.NotFound.class );

  }

  @Test
  void startConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).saveNewConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void startConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).saveNewConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isEqualTo( someError );

  }

  @Test
  void getConversation_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).retrieveConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.getConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void getConversation_ChatRepositoryNotFoundExceptionThrown_ThrowsAsChatServiceNotFoundException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.NotFound( ) ).when( this.repository ).retrieveConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.getConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.NotFound.class );

  }

  @Test
  void getConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).retrieveConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.getConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void getConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).retrieveConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.getConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isEqualTo( someError );

  }

  @Test
  void deleteConversation_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).deleteConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void deleteConversation_ChatRepositoryNotFoundExceptionThrown_ThrowsAsChatServiceNotFoundException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.NotFound( ) ).when( this.repository ).deleteConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.NotFound.class );

  }

  @Test
  void deleteConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).deleteConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void deleteConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).deleteConversation( any( ) );
    val conversationRetrieval = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isEqualTo( someError );

  }

}