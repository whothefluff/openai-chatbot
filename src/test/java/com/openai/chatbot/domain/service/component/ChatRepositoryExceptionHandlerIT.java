package com.openai.chatbot.domain.service.component;

import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.primary.ChatService;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
    Mockito.doThrow( new ChatRepositoryException( ) ).when( this.repository ).saveNewConversation( any( ) );
    val convInsert = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( convInsert ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void startConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).saveNewConversation( any( ) );
    val convInsert = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( convInsert ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void startConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).saveNewConversation( any( ) );
    val convInsert = ( ThrowingCallable )( ) -> this.service.startConversation( "a", "b" );
    // Act & Assert
    assertThatThrownBy( convInsert ).isEqualTo( someError );

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
  void getConversations_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).retrieveConversations( any( ) );
    val conversationsRetrieval = ( ThrowingCallable )( ) -> this.service.getConversations( );
    // Act & Assert
    assertThatThrownBy( conversationsRetrieval ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void getConversations_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).retrieveConversations( any( ) );
    val conversationsRetrieval = ( ThrowingCallable )( ) -> this.service.getConversations( );
    // Act & Assert
    assertThatThrownBy( conversationsRetrieval ).isEqualTo( someError );

  }

  @Test
  void updateConversation_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).updateConversation( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.service.updateConversation( null );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void updateConversation_ChatRepositoryNotFoundExceptionThrown_ThrowsAsChatServiceNotFoundException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.NotFound( ) ).when( this.repository ).updateConversation( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.service.updateConversation( null );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isExactlyInstanceOf( ChatServiceException.NotFound.class );

  }

  @Test
  void updateConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).updateConversation( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.service.updateConversation( null );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void updateConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).updateConversation( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.service.updateConversation( null );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isEqualTo( someError );

  }

  @Test
  void deleteConversation_ChatRepositoryExceptionThrown_ThrowsAsChatServiceException( )
    throws Exception{
    // Arrange
    doThrow( new ChatRepositoryException( ) ).when( this.repository ).deleteConversation( any( ) );
    val convDeletion = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( convDeletion ).isExactlyInstanceOf( ChatServiceException.class );

  }

  @Test
  void deleteConversation_ChatRepositoryNotFoundExceptionThrown_ThrowsAsChatServiceNotFoundException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.NotFound( ) ).when( this.repository ).deleteConversation( any( ) );
    val convDeletion = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( convDeletion ).isExactlyInstanceOf( ChatServiceException.NotFound.class );

  }

  @Test
  void deleteConversation_ChatRepositoryConflictExceptionThrown_ThrowsAsChatServiceConflictException( )
    throws ChatRepositoryException{
    // Arrange
    doThrow( new ChatRepositoryException.Conflict( ) ).when( this.repository ).deleteConversation( any( ) );
    val convDeletion = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( convDeletion ).isExactlyInstanceOf( ChatServiceException.Conflict.class );

  }

  @Test
  void deleteConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.repository ).deleteConversation( any( ) );
    val convDeletion = ( ThrowingCallable )( ) -> this.service.deleteConversation( null );
    // Act & Assert
    assertThatThrownBy( convDeletion ).isEqualTo( someError );

  }

}