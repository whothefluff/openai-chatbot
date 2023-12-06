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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( "MissingJavadoc" )
@SpringBootTest
public class ConversationControllerTest{

  @Autowired
  private ConversationController controller;
  @Autowired
  private ConversationMapper mapper;

  //TODO finish tests (also add naming check logic to chat entity)
  @Test
  public void createConversationSuccessTest( ){

    val someName = "Test Name"; // NON-NLS
    val someMsg = "Test System Message"; // NON-NLS
    val conversationStarterBody = new ConversationStarterBody( ).name( someName ).systemMessage( someMsg );
    val id = UUID.randomUUID( );
    val successfulChatService = new FakeSuccessfulChatService( id );
    val responseEntity = new ConversationController( successfulChatService, this.mapper ).createConversation( conversationStarterBody );
    assertThat( responseEntity.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    val createdConversationBody = new ConversationBody( ).id( id ).name( conversationStarterBody.name( ) );
    assertThat( responseEntity.getBody( ) ).isEqualTo( createdConversationBody );
    /*
    val someName = "Test Name"; // NON-NLS
    val someMsg = "Test System Message"; // NON-NLS
    val conversationStarterBody = new ConversationStarterBody( ).name( someName ).systemMessage( someMsg );
    val successfulChatService = new FakeSuccessfulChatService( UUID.randomUUID( ), someMsg );
    val responseEntity = new ConversationController( successfulChatService, this.mapper ).createConversation( conversationStarterBody );
    assertThat( responseEntity.getStatusCode( ).is2xxSuccessful( ) ).isTrue( );
    val createdConversationBody = new ConversationBody( ).id( UUID.randomUUID( ) ).name( conversationStarterBody.name( ) );
    assertThat( responseEntity.getBody( ) ).isEqualTo( createdConversationBody );
*/
  }

  @Test
  public void createConversationBadRequestTest( ){

    ConversationStarterBody conversationStarterBody = new ConversationStarterBody( );
    conversationStarterBody.name( null );
    conversationStarterBody.systemMessage( null );
    ResponseEntity<?> responseEntity = this.controller.createConversation( conversationStarterBody );
    assertThat( responseEntity.getStatusCode( ).is4xxClientError( ) ).isTrue( );

  }

  @Test
  public void createConversationServerErrorTest( ){

    ConversationStarterBody conversationStarterBody = new ConversationStarterBody( );
    conversationStarterBody.name( "Test Name" );
    conversationStarterBody.systemMessage( "Test System Message" );
    ResponseEntity<?> responseEntity = this.controller.createConversation( conversationStarterBody );
    assertThat( responseEntity.getStatusCode( ).is5xxServerError( ) ).isTrue( );

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
    public Conversation startConversation( final String name, final String systemMessage )
      throws ChatServiceException{

      val request = new ChatRequest( ).messages( new ArrayList<>( 0 ) );
      request.messages( ).add( new ChatRequest.Message( ).content( systemMessage ) );
      val conversation = new Conversation( ).requests( new ArrayList<>( 0 ) ).id( this.id ).name( name );
      conversation.requests( ).add( request );
      return conversation;

    }

  }

}