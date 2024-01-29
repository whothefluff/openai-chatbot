package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@AutoConfigureTestDatabase
class RepositoryForJpaChatsIT{

  @Autowired
  private RepositoryForJpaChats repositoryForJpaChats;
  @Autowired
  private JpaChatRepository jpaRepository;

  @Test
  public void retrieveConversation_ConversationExists_ReturnsConversation( )
    throws ChatRepositoryException{
    // Arrange
    val newId = this.jpaRepository.save( new JpaChat( ) ).id( );
    // Act
    val retrievedConv = this.repositoryForJpaChats.retrieveConversation( newId );
    // Assert
    assertThat( retrievedConv ).isNotNull( );

  }

  @Test
  public void retrieveConversation_ConversationDoesNotExist_ThrowsChatRepositoryExceptionNotFound( )
    throws ChatRepositoryException{
    // Arrange
    val nonexistentId = UUID.randomUUID( );
    val convRetrieval = ( ThrowingCallable )( ) -> this.repositoryForJpaChats.retrieveConversation( nonexistentId );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.NotFound.class ).isThrownBy( convRetrieval );
  }

}
