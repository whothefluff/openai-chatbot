package com.openai.chatbot.domain.service;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.primary.ChatService;
import com.openai.chatbot.domain.port.secondary.ChatCompletionsService;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import static com.openai.chatbot.domain.entity.ChatMessageRoles.SYSTEM;

/**
 * Chat related operations using OpenAI
 */
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@Service
public class OpenAiChatService implements ChatService{

  final ChatCompletionsService remoteSrv;
  final ChatRepository repository;

  @Override
  public Conversation startConversation( final String name, final String systemMessage )
    throws ChatServiceException{

    log.entry( name, systemMessage );
    val saveConv = ( CheckedFunction0<Conversation> )( ) ->
      {
        val conversation = new Conversation( ).name( name );
        val request = new ChatRequest( );
        conversation.requests( ).add( request );
        val msgReq = new ChatRequest.Message( ).role( SYSTEM ).content( systemMessage );
        request.messages( ).add( msgReq );
        return this.repository.saveNewConversation( conversation, request );
      };
    val chatServiceException = ( Function<Throwable, ChatServiceException> )( e ) ->
      {
        log.catching( e );
        val exception = new ChatServiceException( e );
        log.throwing( exception );
        return exception;
      };
    val result = Try.of( saveConv ).getOrElseThrow( chatServiceException );
    log.exit( result );
    return result; //TODO test method

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

    val response = this.remoteSrv.createResponse( null );
    return null;

  }

  @Override
  public Collection<Conversation> getConversationMessages( final UUID chatId ){

    return null;

  }

  @Override
  public void deleteBotMessage( final UUID chatId, final Integer messageId ){

  }

  @Override
  public void deleteUserMessage( final UUID chatId, final Integer messageId ){

  }

}