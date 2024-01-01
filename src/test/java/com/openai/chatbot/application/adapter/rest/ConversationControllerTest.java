package com.openai.chatbot.application.adapter.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationMapper;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationStarterBody;
import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.primary.ChatService;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

@SuppressWarnings( "MissingJavadoc" )
@SpringBootTest 
public class ConversationControllerTest{

  @Test
  public void createConversation_validConversationStart_returnsSuccessfulResponse( ){
    // Arrange
    val someName = "Test Name"; // NON-NLS
    val someMsg = "Test System Message"; // NON-NLS
    val id = UUID.randomUUID( );
    val fakeConversationBody = new ConversationBody( ).id( id ).name( someName );
    val successfulChatService = new FakeSuccessfulChatService( id );
    val mapper = new FakeConversationBodyMapper( fakeConversationBody );
    val conversationStarterBody = new ConversationStarterBody( ).name( someName ).systemMessage( someMsg );
    val createdConversationBody = new ConversationBody( ).id( id ).name( conversationStarterBody.name( ) );

    // Act
    val responseEntity = new ConversationController( successfulChatService, mapper ).createConversation( conversationStarterBody );

    // Assert
    assertThat( responseEntity.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    assertThat( responseEntity.getBody( ) ).isEqualTo( createdConversationBody );
    
  }

  @Test
  public void createConversation_wrongConversationStart_returnsBadRequestResponse( ){
    // Arrange
    val someErrorMsg = "Test Bad Request Message"; // NON-NLS
    val failedChatService = new FakeFailedChatService( someErrorMsg );
    val mapper = new FakeConversationMapper( );
    val conversationStarterBody = new ConversationStarterBody( );

    // Act
    val responseEntity = new ConversationController( failedChatService, mapper ).createConversation( conversationStarterBody );

    // Assert
    assertThat( responseEntity.getStatusCode( ).is4xxClientError( ) ).isTrue( );
    assertThat( responseEntity.getBody( ) ).isEqualTo( someErrorMsg );

  }

  @Test
  public void createConversation_UnexpectedExceptionThrown_returnsServerErrorResponse( ){
    // Arrange
    val someErrorMsg = "Test Server Error Message"; // NON-NLS
    val failedChatService = new FakeChatServiceWithUnexpectedProblem( someErrorMsg );
    val mapper = new FakeConversationMapper( );
    val conversationStarterBody = new ConversationStarterBody( );

    // Act
    val responseEntity = new ConversationController( failedChatService, mapper ).createConversation( conversationStarterBody );

    // Assert
    assertThat( responseEntity.getStatusCode( ).is5xxServerError( ) ).isTrue( );
    assertThat( responseEntity.getBody( ) ).isEqualTo( someErrorMsg );

  }

  //##############################################################################################################

  private static class FakeConversationMapper implements ConversationMapper{

    @Override
    public ConversationBody toDto( final Conversation entity ){

      return null;

    }

    @Override
    public Conversation toEntity( final ConversationBody dto ){

      return null;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class FakeConversationBodyMapper extends FakeConversationMapper{

    final ConversationBody conversationBody;

    @Override
    public ConversationBody toDto( final Conversation entity ){

      return conversationBody;

    }

  }

  private static class FakeChatService implements ChatService{

    @Override
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      return null;

    }

    @Override
    public Collection<Conversation> getConversations( ){

      return null;
    }

    @Override
    public Conversation getConversation( final UUID id ){

      return null;
    }

    @Override
    public Conversation updateConversation( final UUID id, final Conversation conversation ){

      return null;
    }

    @Override
    public void deleteConversation( final UUID chatId ){

    }

    @Override
    public ChatResponse addUserMessage( final UUID chatId, final ChatRequest userMessage ){

      return null;

    }

    @Override
    public Collection<?> getConversationMessages( final UUID chatId ){

      return null;

    }

    @Override
    public void deleteBotMessage( final UUID chatId, final Integer messageId ){

    }

    @Override
    public void deleteUserMessage( final UUID chatId, final Integer messageId ){

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class FakeSuccessfulChatService extends FakeChatService{

    final UUID id;

    @Override
    public Conversation startConversation( final String name, 
                                           final String systemMessage )
      throws ChatServiceException{

      return new Conversation( );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class FakeFailedChatService extends FakeChatService{

    final String errorMessage;

    @Override
    public Conversation startConversation( final String name, 
                                           final String systemMessage )
      throws ChatServiceException{

      throw new ChatServiceException( errorMessage );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class FakeChatServiceWithUnexpectedProblem extends FakeChatService{

    final String errorMessage;

    @Override
    public Conversation startConversation( final String name, 
                                           final String systemMessage )
      throws ChatServiceException{

      throw new RuntimeException( errorMessage );

    }

  }

}