package com.openai.chatbot.domain.service;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Chat related operations using OpenAI
 */
@SuppressWarnings( "RedundantThrows" )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED,
                makeFinal = true )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@Service
class OpenAiChatService implements ChatService{

  ChatCompletionsService remoteSrv;
  ChatRepository repository;
  private Supplier<SortedSet<Conversation>> newSortedConversations = ( ) ->
    {
      val creationMoment = ( Function<Conversation, Instant> )Conversation::createdAt;
      val modificationMoment = ( Function<Conversation, Instant> )Conversation::modifiedAt;
      val id = ( Function<Conversation, UUID> )Conversation::id;
      val byCreationAndModification = ( Comparator<Conversation> )Comparator.comparing( creationMoment )
                                                                            .thenComparing( modificationMoment )
                                                                            .thenComparing( id );
      return new TreeSet<>( byCreationAndModification );
    };

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

    log.entry( );
    val convsRetrieval = ( CheckedFunction0<Collection<Conversation>> )( ) ->
      {
        val sortedByCreationAndModification = Sort.by( Sort.Direction.DESC, "createdAt", "modifiedAt", "id" );
        return this.repository.retrieveConversations( sortedByCreationAndModification );
      };
    val toSortedSet = ( Function<Collection<Conversation>, SortedSet<Conversation>> )( convs ) ->
      {
        val sortedConvs = this.newSortedConversations.get( );
        sortedConvs.addAll( convs );
        return sortedConvs;
      };
    val result = Try.of( convsRetrieval )
                    .map( toSortedSet )
                    .get( );
    return log.exit( result );

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
    //noinspection SerializableStoresNonSerializable
    val convUpdate = ( CheckedFunction0<Conversation> )( ) -> this.repository.updateConversation( conversation );
    val result = Try.of( convUpdate )
                    .get( );
    return log.exit( result );

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