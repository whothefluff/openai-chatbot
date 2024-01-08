package com.openai.chatbot.domain.service;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

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

@SuppressWarnings( "MissingJavadoc" )
@SpringBootTest
public class OpenAiChatServiceTest {

  @Test
  void startConversation_SuccessfulCase_ConversationStarted( ) throws ChatServiceException {
    // Arrange
    val name = "testName";
    val systemMessage = "testSystemMessage";
    val msgReq = new ChatRequest.Message( ).role( system ).content( systemMessage );
    val request = new ChatRequest( ).addMessage( msgReq );
    val expectedConversation = new Conversation( ).name( name ).addRequest( request );

    // Act
    val createdConversation = new OpenAiChatService( new FakeChatCompletionsService( ), new FakeSuccessfulChatRepository( ) ).startConversation( name, systemMessage );

    // Assert
    assertEquals( expectedConversation, createdConversation );

  }

  @Test
  void startConversation_ErrorOccurs_ThrowsChatServiceException( ) {
    // Arrange
    val name = "testName";
    val systemMessage = "testSystemMessage";
    val startConversationWithNoName = ( Executable )( ) -> new OpenAiChatService( new FakeChatCompletionsService( ), new FakeUnsuccessfulChatRepository( ) ).startConversation( name, systemMessage );

    // Act & Assert
    assertThrows( ChatServiceException.class, 
                  startConversationWithNoName );

  }

  //##############################################################################################################

    private static class FakeChatRepository implements ChatRepository {

        @Override
        public Conversation saveNewConversation( Conversation chat )
                throws ChatRepositoryException {

            return null;

        }

        @Override
        public void deleteConversation(Conversation chat) throws ChatRepositoryException {
            
        }

        @Override
        public Conversation updateConversation(Conversation chat) throws ChatRepositoryException {

            return null;
            
        }

        @Override
        public Conversation addRequestToChat(Conversation chat, ChatRequest chatRequest) {

            return null;
            
        }

        @Override
        public Conversation addResponseToChat(Conversation chat, ChatResponse chatResponse) {

            return null;
            
        }

        @Override
        public void deleteResponse(ChatResponse chatResponse) throws ChatRepositoryException {
            
        }

        @Override
        public void deleteRequest(ChatRequest chatRequest) throws ChatRepositoryException {
            
        }

        @Override
        public Collection<Conversation> searchChatByContent(String content) throws ChatRepositoryException {

            return null;
            
        }
    
    }

    private static class FakeChatCompletionsService implements ChatCompletionsService {

        @Override
        public ChatResponse createResponse( ChatRequest request ) {
            
            return null;
            
        }
    
    }

    @EqualsAndHashCode( callSuper = true )
    @Data
    private static class FakeSuccessfulChatRepository extends FakeChatRepository {

        @Override
        public Conversation saveNewConversation( Conversation conversation )
                throws ChatRepositoryException {

            return conversation;

        }
    
    }

    @EqualsAndHashCode( callSuper = true )
    @Data
    private static class FakeUnsuccessfulChatRepository extends FakeChatRepository {

        @Override
        public Conversation saveNewConversation( Conversation conversation )
                throws ChatRepositoryException {

            throw new RuntimeException( );

        }
    
    }

}
