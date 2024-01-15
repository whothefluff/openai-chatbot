package com.openai.chatbot.application.adapter.rest;

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
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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
    val successfulChatService = new SuccessfulConversationStartStub( id );
    val mapper = new ConversationBodyMapperStub( fakeConversationBody );
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
    val failedChatService = new FailedConversationStartStub( someErrorMsg );
    val mapper = new ConversationMapperDummy( );
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
    val failedChatService = new UnexpectedFailedConversationStartStub( someErrorMsg );
    val mapper = new ConversationMapperDummy( );
    val conversationStarterBody = new ConversationStarterBody( );
    // Act
    val responseEntity = new ConversationController( failedChatService, mapper ).createConversation( conversationStarterBody );
    // Assert
    assertThat( responseEntity.getStatusCode( ).is5xxServerError( ) ).isTrue( );
    assertThat( responseEntity.getBody( ) ).isEqualTo( someErrorMsg );

  }

  @Test
  public void deleteConversation_SuccessfulDeletion_NoContentReturned( ){
    // Arrange
    val id = UUID.randomUUID( );
    val successfulChatService = new SuccessfulConversationDeleteStub( id );
    val mapper = new ConversationMapperDummy( );
    // Act
    val response = new ConversationController( successfulChatService, mapper ).deleteConversation( id );
    // Assert
    assertThat( response.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );

  }

  @Test
  public void deleteConversation_ServiceError_NotFoundReturned( ){
    // Arrange
    val id = UUID.randomUUID( );
    val failedChatService = new FailedConversationDeleteStub( );
    val mapper = new ConversationMapperDummy( );
    // Act
    val response = new ConversationController( failedChatService, mapper ).deleteConversation( id );
    // Assert
    assertThat( response.getStatusCode( ).is4xxClientError( ) ).isTrue( );

  }

  @Test
  public void deleteConversation_InternalError_InternalServerErrorReturned( ){
    // Arrange
    val id = UUID.randomUUID( );
    val someErrorMsg = "Test Server Error Message"; // NON-NLS
    val failedChatService = new UnexpectedFailedConversationDeleteStub( someErrorMsg );
    val mapper = new ConversationMapperDummy( );
    // Act
    val response = new ConversationController( failedChatService, mapper ).deleteConversation( id );
    // Assert
    assertThat( response.getStatusCode( ).is5xxServerError( ) ).isTrue( );

  }
  //##############################################################################################################

  private static class ConversationMapperDouble implements ConversationMapper{

    @Override
    public @Nullable ConversationBody toDto( final Conversation entity ){

      return null;

    }

    @Override
    public @Nullable Conversation toEntity( final ConversationBody dto ){

      return null;

    }

  }

  private static class ConversationMapperDummy extends ConversationMapperDouble{

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationBodyMapperStub extends ConversationMapperDouble{

    final ConversationBody conversationBody;

    @Override
    public ConversationBody toDto( final Conversation entity ){

      return this.conversationBody;

    }

  }

  private static class ChatServiceDouble implements ChatService{

    @Override
    public @Nullable Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      return null;

    }

    @Override
    public @Nullable Collection<Conversation> getConversations( ){

      return null;

    }

    @Override
    public @Nullable Conversation getConversation( final UUID id ){

      return null;

    }

    @Override
    public @Nullable Conversation updateConversation( final UUID id, final Conversation conversation ){

      return null;

    }

    @Override
    public void deleteConversation( final UUID chatId )
      throws ChatServiceException{

    }

    @Override
    public @Nullable ChatResponse addUserMessage( final UUID chatId, final ChatRequest userMessage ){

      return null;

    }

    @Override
    public @Nullable Collection<?> getConversationMessages( final UUID chatId ){

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
  private static class SuccessfulConversationStartStub extends ChatServiceDouble{

    final UUID id;

    @Override
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      return new Conversation( );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationDeleteStub extends ChatServiceDouble{

    final UUID id;

    @Override
    public void deleteConversation( final UUID chatId )
      throws ChatServiceException{

      super.deleteConversation( chatId );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class FailedConversationStartStub extends ChatServiceDouble{

    final String errorMessage;

    @Override
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      throw new ChatServiceException( this.errorMessage );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  private static class FailedConversationDeleteStub extends ChatServiceDouble{

    @Override
    public void deleteConversation( final UUID chatId )
      throws ChatServiceException{

      throw new ChatServiceException( );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class UnexpectedFailedConversationStartStub extends ChatServiceDouble{

    final String errorMessage;

    @Override
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      throw new RuntimeException( this.errorMessage );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class UnexpectedFailedConversationDeleteStub extends ChatServiceDouble{

    final String errorMessage;

    @Override
    public void deleteConversation( final UUID chatId )
      throws ChatServiceException{

      throw new RuntimeException( this.errorMessage );

    }

  }

}