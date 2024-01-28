package com.openai.chatbot.application.adapter.rest.component;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ConversationUpdateParameterCheckImplTest{

  @Test
  void validate_PathIdAndBodyIdMatch_NoException( ){
    // Arrange
    val id = UUID.randomUUID( );
    val conversationBody = new ConversationBody( ).id( id );
    val validation = ( ThrowingCallable )( ) -> new ConversationUpdateParameterCheckImpl( ).validate( id, conversationBody );
    // Act & Assert
    assertThatNoException( ).isThrownBy( validation );

  }

  @Test
  void validate_PathIdAndBodyIdDoNotMatch_ThrowsResponseStatusException( ){
    // Arrange
    val id = UUID.randomUUID( );
    val conversationBody = new ConversationBody( ).id( UUID.randomUUID( ) );
    val validation = ( ThrowingCallable )( ) -> new ConversationUpdateParameterCheckImpl( ).validate( id, conversationBody );
    // Act & Assert
    assertThatExceptionOfType( ResponseStatusException.class ).isThrownBy( validation );
  }

}