package com.openai.chatbot.intrastructure.persistence.repository.component;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.repository.JpaChatRepository;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class DataAccessExceptionsHandlerIT{

  @SpyBean
  private JpaChatRepository jpaChatRepository;
  @Autowired
  private ChatRepository repositoryForJpaChats;

  @Test
  void saveNewConversation_DataIntegrityViolationExceptionThrown_ThrowsConflictException( )
    throws ChatRepositoryException{
    // Arrange
    val JpaChat = new JpaChat( ).id( UUID.randomUUID( ) );
    doThrow( new SomeDataIntegrityViolationException( ) ).when( this.jpaChatRepository ).save( JpaChat );
    val convInsert = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.saveNewConversation( new Conversation( ).id( JpaChat.id( ) ) );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.Conflict.class ).isThrownBy( convInsert );

  }

  @Test
  void saveNewConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.jpaChatRepository ).save( any( ) );
    val convInsert = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.saveNewConversation( any( ) );
    // Act & Assert
    assertThatThrownBy( convInsert ).isEqualTo( someError );

  }

  @Test
  void updateConversation_DataIntegrityViolationExceptionThrown_ThrowsConflictException( )
    throws ChatRepositoryException{
    // Arrange
    val conversation = new Conversation( ).id( UUID.randomUUID( ) );
    when( this.jpaChatRepository.findById( conversation.id( ) ) ).thenReturn( Optional.of( new JpaChat( ) ) );
    doThrow( new SomeDataIntegrityViolationException( ) ).when( this.jpaChatRepository ).save( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.updateConversation( conversation );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.Conflict.class ).isThrownBy( convUpdate );

  }

  @Test
  void updateConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val conversation = new Conversation( ).id( UUID.randomUUID( ) );
    when( this.jpaChatRepository.findById( conversation.id( ) ) ).thenReturn( Optional.of( new JpaChat( ) ) );
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.jpaChatRepository ).save( any( ) );
    val convUpdate = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.updateConversation( conversation );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isEqualTo( someError );

  }

  @Test
  void deleteConversation_DataIntegrityViolationExceptionThrown_ThrowsConflictException( )
    throws ChatRepositoryException{
    // Arrange
    val conversation = new Conversation( ).id( UUID.randomUUID( ) );
    when( this.jpaChatRepository.findById( conversation.id( ) ) ).thenReturn( Optional.of( new JpaChat( ).id( conversation.id( ) ) ) );
    doThrow( new SomeDataIntegrityViolationException( ) ).when( this.jpaChatRepository ).delete( any( ) );
    val convDelete = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.deleteConversation( conversation.id( ) );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.Conflict.class ).isThrownBy( convDelete );

  }

  @Test
  void deleteConversation_RuntimeExceptionThrown_RethrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val conversation = new Conversation( ).id( UUID.randomUUID( ) );
    when( this.jpaChatRepository.findById( conversation.id( ) ) ).thenReturn( Optional.of( new JpaChat( ).id( conversation.id( ) ) ) );
    val someError = new RuntimeException( );
    doThrow( someError ).when( this.jpaChatRepository ).delete( any( ) );
    val convDelete = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.deleteConversation( conversation.id( ) );
    // Act & Assert
    assertThatThrownBy( convDelete ).isEqualTo( someError );

  }

  private static class SomeDataIntegrityViolationException extends DataIntegrityViolationException{

    public SomeDataIntegrityViolationException( ){

      super( "" );

    }

  }

}