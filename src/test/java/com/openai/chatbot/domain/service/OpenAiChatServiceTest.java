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
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.UUID;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SuppressWarnings( "MissingJavadoc" )
@SpringBootTest
public class OpenAiChatServiceTest{

  @Test
  void startConversation_SuccessfulCase_ConversationStarted( )
    throws ChatServiceException{
    // Arrange
    val name = "testName";
    val systemMessage = "testSystemMessage";
    val msgReq = new ChatRequest.Message( ).role( system ).content( systemMessage );
    val request = new ChatRequest( ).addMessage( msgReq );
    val expectedConversation = new Conversation( ).name( name ).addRequest( request );
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new SuccessfulConversationSaveStub( ) );
    // Act
    val createdConversation = service.startConversation( name, systemMessage );
    // Assert
    assertThat( createdConversation ).isEqualTo( expectedConversation );

  }

  @Test
  void startConversation_ErrorOccurs_ThrowsChatServiceException( ){
    // Arrange
    val name = "testName";
    val systemMessage = "testSystemMessage";
    val service = new OpenAiChatService( new ChatCompletionsServiceDummy( ), new UnsuccessfulConversationSaveStub( ) );
    val startConversationWithNoName = ( ThrowingCallable )( ) -> service.startConversation( name, systemMessage );
    // Act & Assert
    assertThatThrownBy( startConversationWithNoName ).isInstanceOf( ChatServiceException.class );

  }

  @Test
  void deleteConversation_SuccessfulCase_NothingReturned( )
    throws ChatServiceException{
    // Arrange
    val id = UUID.randomUUID( );
    val deleteConversation = ( Executable )( ) -> new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                                                         new SuccessfulConversationDeleteStub( ) ).deleteConversation( id );
    // Act & Assert
    assertDoesNotThrow( deleteConversation );

  }

  @Test
  void deleteConversation_ErrorOccurs_ThrowsChatServiceException( ){
    // Arrange
    val id = UUID.randomUUID( );
    val deleteConversation = ( ThrowingCallable )( ) -> new OpenAiChatService( new ChatCompletionsServiceDummy( ),
                                                                               new UnsuccessfulConversationDeleteStub( ) ).deleteConversation( id );
    // Act & Assert
    assertThatThrownBy( deleteConversation ).isInstanceOf( ChatServiceException.class );

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
  private static class UnsuccessfulConversationSaveStub extends ChatRepositoryDouble{

    @Override
    public Conversation saveNewConversation( final Conversation conversation )
      throws ChatRepositoryException{

      throw new RuntimeException( );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class UnsuccessfulConversationDeleteStub extends ChatRepositoryDouble{

    @Override
    public void deleteConversation( final UUID id )
      throws ChatRepositoryException{

      throw new RuntimeException( );

    }

  }

}
