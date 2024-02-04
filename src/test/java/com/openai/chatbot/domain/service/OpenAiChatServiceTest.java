package com.openai.chatbot.domain.service;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.secondary.ChatCompletionsService;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.*;

class OpenAiChatServiceTest{

  @Test
  void startConversation_SuccessfulCase_ConversationStarted( )
    throws ChatServiceException{
    // Arrange
    val name = "testName";
    val model = "testModel";
    val systemMessage = "testSystemMessage";
    val msgReq = new ChatRequest.Message( ).role( system ).content( systemMessage );
    val request = new ChatRequest( ).model( model ).addMessage( msgReq );
    val expectedConversation = new Conversation( ).name( name ).addRequest( request );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new SuccessfulConversationSaveStub( ) );
    // Act
    val createdConversation = service.startConversation( name, model, systemMessage );
    // Assert
    assertThat( createdConversation ).isEqualTo( expectedConversation );

  }

  @Test
  void startConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val name = "testName";
    val model = "testModel";
    val systemMessage = "testSystemMessage";
    val someExc = new ChatRepositoryException( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new ConversationInsertExceptionStub( someExc ) );
    val failedInsert = ( ThrowingCallable )( ) -> service.startConversation( name, model, systemMessage );
    // Act & Assert
    assertThatThrownBy( failedInsert ).isEqualTo( someExc );

  }

  @Test
  void deleteConversation_SuccessfulCase_NothingReturned( )
    throws ChatServiceException{
    // Arrange
    val id = UUID.randomUUID( );
    val deleteConversation = ( ThrowingCallable )( ) -> new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                                                               new SuccessfulConversationDeleteStub( ) ).deleteConversation( id );
    // Act & Assert
    assertThatNoException( ).isThrownBy( deleteConversation );

  }

  @Test
  void deleteConversation_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val id = UUID.randomUUID( );
    val someExc = new ChatRepositoryException( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                         new ConversationDeletionExceptionStub( someExc ) );
    val failedDeletion = ( ThrowingCallable )( ) -> service.deleteConversation( id );
    // Act & Assert
    assertThatThrownBy( failedDeletion ).isEqualTo( someExc );

  }

  @Test
  void getConversation_SuccessfulCase_ConversationReturned( )
    throws ChatServiceException{
    // Arrange
    val id = UUID.randomUUID( );
    val expectedConv = new Conversation( ).id( id );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new SuccessfulConversationRetrievalStub( expectedConv ) );
    // Act
    val retrievedConv = service.getConversation( id );
    // Assert
    assertThat( retrievedConv ).isEqualTo( expectedConv );

  }

  @Test
  void getConversation_ErrorOccurs_ThrowsExceptionAsIs( )
    throws ChatServiceException{
    // Arrange
    val id = UUID.randomUUID( );
    val someExc = new ChatRepositoryException( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                         new ConversationRetrievalExceptionStub( someExc ) );
    val failedRetrieval = ( ThrowingCallable )( ) -> service.getConversation( id );
    // Act & Assert
    assertThatThrownBy( failedRetrieval ).isEqualTo( someExc );

  }

  @Test
  void getConversations_NormalScenario_RepositoryIsCalledWithSorting( )
    throws ChatServiceException{
    // Arrange
    val repository = new ChatRepositoryRetrieveConvsSpy( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), repository );
    // Act
    service.getConversations( );
    // Assert
    assertThat( repository.wasCalledWithValidSorting( ) ).isTrue( );

  }

  @Test
  void getConversations_RepositoryReturnsEntries_SortedSetReturned( )
    throws ChatServiceException{
    // Arrange
    val conversations = Collections.singletonList( new Conversation( )
                                                     .id( UUID.randomUUID( ) )
                                                     .createdAt( Instant.now( ) )
                                                     .modifiedAt( Instant.now( ) ) );
    val repository = new SuccessfulConversationsRetrievalStub( conversations );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), repository );
    // Act
    val sortedConvs = service.getConversations( );
    // Assert
    assertThat( sortedConvs ).isNotNull( );

  }

  @Test
  void getConversations_ErrorOccurs_ThrowsExceptionAsIs( ){
    // Arrange
    val someExc = new ChatRepositoryException( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                         new ConversationRetrievalsExceptionStub( someExc ) );
    // Act & Assert
    assertThatThrownBy( service::getConversations ).isEqualTo( someExc );

  }

  @Test
  void updateConversation_SuccessfulCase_ConversationReturned( )
    throws ChatServiceException{
    // Arrange
    val id = UUID.randomUUID( );
    val conversation = new Conversation( ).id( id );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new SuccessfulConversationUpdateStub( conversation ) );
    // Act
    val retrievedConv = service.updateConversation( conversation );
    // Assert
    assertThat( retrievedConv ).isEqualTo( conversation );

  }

  @Test
  void updateConversation_ErrorOccurs_ThrowsExceptionAsIs( )
    throws ChatServiceException{
    // Arrange
    val someExc = new ChatRepositoryException( );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new ConversationUpdateExceptionStub( someExc ) );
    val failedRetrieval = ( ThrowingCallable )( ) -> service.updateConversation( null );
    // Act & Assert
    assertThatThrownBy( failedRetrieval ).isEqualTo( someExc );

  }
  //##############################################################################################################

  private static class ChatRepositoryDouble implements ChatRepository{

    @Override
    public @Nullable Conversation saveNewConversation( final Conversation chat )
      throws ChatRepositoryException{

      return null;

    }

    @Override
    public @Nullable Conversation retrieveConversation( final UUID id )
      throws ChatRepositoryException{

      return null;

    }

    @Override
    public @Nullable Collection<Conversation> retrieveConversations( final Sort sorting )
      throws ChatRepositoryException{

      return null;

    }

    @Override
    public @Nullable Conversation updateConversation( final Conversation chat )
      throws ChatRepositoryException{

      return null;

    }

    @Override
    public void deleteConversation( final UUID id )
      throws ChatRepositoryException{

    }

    @Override
    public @Nullable Conversation addRequestToChat( final Conversation chat, final ChatRequest chatRequest ){

      return null;

    }

    @Override
    public @Nullable Conversation addResponseToChat( final Conversation chat, final ChatResponse chatResponse ){

      return null;

    }

    @Override
    public void deleteResponse( final ChatResponse chatResponse )
      throws ChatRepositoryException{

    }

    @Override
    public void deleteRequest( final ChatRequest chatRequest )
      throws ChatRepositoryException{

    }

    @Override
    public @Nullable Collection<Conversation> searchChatByContent( final String content )
      throws ChatRepositoryException{

      return null;

    }

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class SuccessfulConversationsRetrievalStub extends ChatRepositoryDouble{

    final List<Conversation> conversations;

    @Override
    public @Nullable Collection<Conversation> retrieveConversations( final Sort sorting )
      throws ChatRepositoryException{

      return Collections.unmodifiableList( this.conversations );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  @Accessors( chain = true,
              fluent = true )
  private static class ChatRepositoryRetrieveConvsSpy extends ChatRepositoryDouble{

    Boolean wasCalledWithValidSorting = FALSE;

    @Override
    public @Nullable Collection<Conversation> retrieveConversations( final Sort sorting )
      throws ChatRepositoryException{

      if( sorting.isSorted( ) ){
        this.wasCalledWithValidSorting = TRUE;
      }
      return Collections.emptyList( );

    }

  }

  private static class ChatCompletionsServiceDummy implements ChatCompletionsService{

    @Override
    public @Nullable ChatResponse createResponse( final ChatRequest request ){

      return null;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationSaveStub extends ChatRepositoryDouble{

    @Override
    public Conversation saveNewConversation( final Conversation conversation )
      throws ChatRepositoryException{

      return conversation;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationDeleteStub extends ChatRepositoryDouble{

    @Override
    public void deleteConversation( final UUID id )
      throws ChatRepositoryException{

      super.deleteConversation( id );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationRetrievalStub extends ChatRepositoryDouble{

    final Conversation conversation;

    @Override
    public Conversation retrieveConversation( final UUID id )
      throws ChatRepositoryException{

      return this.conversation;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class SuccessfulConversationUpdateStub extends ChatRepositoryDouble{

    final Conversation conversation;

    @Override
    public Conversation updateConversation( final Conversation conversation )
      throws ChatRepositoryException{

      return this.conversation;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationInsertExceptionStub extends ChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation saveNewConversation( final Conversation conversation )
      throws ChatRepositoryException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationDeletionExceptionStub extends ChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public void deleteConversation( final UUID id )
      throws ChatRepositoryException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationRetrievalExceptionStub extends ChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation retrieveConversation( final UUID id )
      throws ChatRepositoryException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationUpdateExceptionStub extends ChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Conversation updateConversation( final Conversation conversation )
      throws ChatRepositoryException{

      throw this.exception;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationRetrievalsExceptionStub extends ChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public Collection<Conversation> retrieveConversations( final Sort sorting )
      throws ChatRepositoryException{

      throw this.exception;

    }

  }

}
