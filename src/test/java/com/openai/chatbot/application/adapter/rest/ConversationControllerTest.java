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
import lombok.SneakyThrows;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class ConversationControllerTest{

  @AfterEach
  void teardownServletRequest( ){

    RequestContextHolder.resetRequestAttributes( );

  }

  @Test
  void createConversation_ValidConversationStart_ReturnsOkAndNewConversation( ){
    // Arrange
    this.setupServletRequest( );
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
    assertThat( responseEntity.getStatusCode( ) ).isEqualTo( CREATED );
    assertThat( responseEntity.getBody( ) ).isEqualTo( createdConversationBody );

  }

  void setupServletRequest( ){

    RequestContextHolder.setRequestAttributes( new ServletRequestAttributes( new MockHttpServletRequest( ) ) );

  }

  @Test
  void createConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val someErrorMsg = "500 Server Error Message"; // NON-NLS
    val failedChatService = new ConversationStartExceptionStub( new RuntimeException( someErrorMsg ) );
    val mapper = new ConversationMapperDummy( );
    val starterBody = new ConversationStarterBody( );
    val conversationCreation = ( ThrowingCallable )( ) -> new ConversationController( failedChatService, mapper ).createConversation( starterBody );
    // Act & Assert
    assertThatThrownBy( conversationCreation ).isInstanceOf( RuntimeException.class ).hasMessage( someErrorMsg );

  }

  @Test
  void deleteConversation_SuccessfulDeletion_ReturnsOk( ){
    // Arrange
    val id = UUID.randomUUID( );
    val successfulChatService = new SuccessfulConversationDeleteStub( id );
    val mapper = new ConversationMapperDummy( );
    // Act
    val response = new ConversationController( successfulChatService, mapper ).deleteConversation( id );
    // Assert
    assertThat( response.getStatusCode( ) ).isEqualTo( NO_CONTENT );

  }

  @Test
  void deleteConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val id = UUID.randomUUID( );
    val someErrorMsg = "Test Server Error Message"; // NON-NLS
    val failedChatService = new ConversationErasureExceptionStub( new RuntimeException( someErrorMsg ) );
    val mapper = new ConversationMapperDummy( );
    val conversationDeletion = ( ThrowingCallable )( ) -> new ConversationController( failedChatService, mapper ).deleteConversation( id );
    // Act & Assert
    assertThatThrownBy( conversationDeletion ).isInstanceOf( RuntimeException.class ).hasMessage( someErrorMsg );

  }

  @Test
  void getConversation_SuccessfulGet_ReturnsOkAndConversation( ){
    // Arrange
    val id = UUID.randomUUID( );
    val successfulChatService = new SuccessfulConversationRetrievalStub( );
    val conversation = new ConversationBody( ).id( id );
    val mapper = new ConversationBodyMapperStub( conversation );
    // Act
    val response = new ConversationController( successfulChatService, mapper ).getConversation( id );
    // Assert
    assertThat( response.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    assertThat( response.getBody( ) ).isEqualTo( conversation );

  }

  @Test
  void getConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val id = UUID.randomUUID( );
    val someErrorMsg = "500 Server Error Message"; // NON-NLS
    val failedChatService = new ConversationRetrievalExceptionStub( new RuntimeException( someErrorMsg ) );
    val mapper = new ConversationMapperDummy( );
    val conversationRetrieval = ( ThrowingCallable )( ) -> new ConversationController( failedChatService, mapper ).getConversation( id );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isInstanceOf( RuntimeException.class ).hasMessage( someErrorMsg );

  }

  @Test
  void getConversations_SuccessfulGet_ReturnsOkAndConversations( )
    throws ChatServiceException{
    // Arrange
    val convsDto = List.of( new ConversationBody( ).id( UUID.randomUUID( ) ).createdAt( Instant.now( ) ),
                            new ConversationBody( ).id( UUID.randomUUID( ) ).createdAt( Instant.now( ).plusSeconds( 1L ) ) );
    val conversations = convsDto.stream( )
                                .map( c -> new Conversation( ).id( c.id( ) ).createdAt( c.createdAt( ) ) )
                                .collect( Collectors.toCollection( ( ) -> new TreeSet<>( Comparator.comparing( Conversation::createdAt ) ) ) );
    val successfulChatService = new SuccessfulConversationsRetrievalStub( conversations );
    val mapper = new ConversationBodiesMapperStub( convsDto.stream( ).collect( Collectors.toMap( ConversationBody::id, cb -> cb ) ) );
    // Act
    val response = new ConversationController( successfulChatService, mapper ).getConversations( );
    // Assert
    assertThat( response.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    assertThat( response.getBody( ) ).isEqualTo( convsDto );

  }

  @Test
  void getConversations_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val someErrorMsg = "500 Server Error Message"; // NON-NLS
    val failedChatService = new ConversationsRetrievalExceptionStub( new RuntimeException( someErrorMsg ) );
    val mapper = new ConversationMapperDummy( );
    val convsRetrieval = ( ThrowingCallable )( ) -> new ConversationController( failedChatService, mapper ).getConversations( );
    // Act & Assert
    assertThatThrownBy( convsRetrieval ).isInstanceOf( RuntimeException.class ).hasMessage( someErrorMsg );

  }

  @Test
  void updateConversation_SuccessfulUpdate_ReturnsOkAndUpdatedConversation( ){
    // Arrange
    val id = UUID.randomUUID( );
    val successfulChatService = new SuccessfulConversationUpdateStub( );
    val conversation = new ConversationBody( ).id( id );
    val mapper = new ConversationBodyMapperStub( conversation );
    // Act
    val response = new ConversationController( successfulChatService, mapper ).updateConversation( id, conversation );
    // Assert
    assertThat( response.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    assertThat( response.getBody( ) ).isEqualTo( conversation );

  }

  @Test
  void updateConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val id = UUID.randomUUID( );
    val someErrorMsg = "500 Server Error Message"; // NON-NLS
    val failedChatService = new ConversationUpdateExceptionStub( new RuntimeException( someErrorMsg ) );
    val mapper = new ConversationMapperDummy( );
    val conversationRetrieval = ( ThrowingCallable )( ) -> new ConversationController( failedChatService, mapper ).updateConversation( id, null );
    // Act & Assert
    assertThatThrownBy( conversationRetrieval ).isInstanceOf( RuntimeException.class ).hasMessage( someErrorMsg );

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

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationBodiesMapperStub extends ConversationMapperDouble{

    final Map<UUID, ConversationBody> conversationBodies;

    @Override
    public ConversationBody toDto( final Conversation entity ){

      return this.conversationBodies.get( entity.id( ) );

    }

  }

  private static class ChatServiceDouble implements ChatService{

    @Override
    public @Nullable Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      return null;

    }

    @Override
    public @Nullable SortedSet<Conversation> getConversations( )
      throws ChatServiceException{

      return null;

    }

    @Override
    public @Nullable Conversation getConversation( final UUID id )
      throws ChatServiceException{

      return null;

    }

    @Override
    public @Nullable Conversation updateConversation( final UUID id, final Conversation conversation )
      throws ChatServiceException{

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
  private static class SuccessfulConversationUpdateStub extends ChatServiceDouble{

    @Override
    public Conversation updateConversation( final UUID id, final Conversation conversation )
      throws ChatServiceException{

      return conversation;

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
  private static class SuccessfulConversationRetrievalStub extends ChatServiceDouble{

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationsRetrievalStub extends ChatServiceDouble{

    final SortedSet<Conversation> conversations;

    @Override
    public SortedSet<Conversation> getConversations( ){

      return Collections.unmodifiableSortedSet( this.conversations );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationRetrievalExceptionStub extends ChatServiceDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation getConversation( final UUID id )
      throws ChatServiceException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationUpdateExceptionStub extends ChatServiceDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation updateConversation( final UUID id, final Conversation conversation )
      throws ChatServiceException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationsRetrievalExceptionStub extends ChatServiceDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public SortedSet<Conversation> getConversations( )
      throws ChatServiceException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationStartExceptionStub extends ChatServiceDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationErasureExceptionStub extends ChatServiceDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public void deleteConversation( final UUID chatId )
      throws ChatServiceException{

      throw this.exception;

    }

  }

}