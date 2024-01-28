package com.openai.chatbot.domain.service;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.primary.ChatService;
import com.openai.chatbot.domain.port.secondary.ChatCompletionsService;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;

/**
 * Chat related operations using OpenAI
 */
@SuppressWarnings( "RedundantThrows" )
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
    val convInsertion = ( CheckedFunction0<Conversation> )( ) ->
      {
        val conversation = Conversation.initialStateBuilder( ).name( name ).systemMessage( systemMessage ).build( );
        return this.repository.saveNewConversation( conversation );
      };
    val savedConv = Try.of( convInsertion )
                       .get( );
    return log.exit( savedConv );

  }

  @Override
  public SortedSet<Conversation> getConversations( )
    throws ChatServiceException{

    val creationMoment = ( Function<Conversation, Instant> )Conversation::createdAt;
    val id = ( Function<Conversation, UUID> )Conversation::id;
    val compareByCreation = Comparator.comparing( creationMoment ).thenComparing( id );
    val treeSet = ( SortedSet<Conversation> )new TreeSet<>( compareByCreation );
    try{
      treeSet.addAll( this.repository.retrieveConversations( ) );
      return treeSet;
    } catch( final ChatRepositoryException e ){
      throw new RuntimeException( e );
    }

  }

  @Override
  public Conversation getConversation( final UUID id )
    throws ChatServiceException{

    log.entry( id );
    val convRetrieval = ( CheckedFunction0<Conversation> )( ) -> this.repository.retrieveConversation( id );
    val result = Try.of( convRetrieval )
                    .get( );
    return log.exit( result );

  }

  @Override
  public Conversation updateConversation( final Conversation conversation )
    throws ChatServiceException{

    log.entry( conversation );
    return null;

  }

  @Override
  public void deleteConversation( final UUID id )
    throws ChatServiceException{

    log.entry( id );
    val convDeletion = ( CheckedRunnable )( ) -> this.repository.deleteConversation( id );
    Try.run( convDeletion )
       .get( );
    log.exit( );

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