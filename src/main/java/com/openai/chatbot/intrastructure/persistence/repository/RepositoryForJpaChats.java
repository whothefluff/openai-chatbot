package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ChatRequestMapper;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ConversationMapper;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.repository.component.HandleDataAccessExceptions;
import io.vavr.CheckedFunction0;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings( { "SerializableStoresNonSerializable", "RedundantThrows" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED,
                makeFinal = true )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@Component
class RepositoryForJpaChats implements ChatRepository{

  JpaChatRepository jpaRepository;
  ConversationMapper conversationMapper;
  ChatRequestMapper chatRequestMapper;

  @HandleDataAccessExceptions
  @Override
  public Conversation saveNewConversation( final Conversation chat )
    throws ChatRepositoryException{

    log.entry( chat );
    val saveConv = ( CheckedFunction0<Conversation> )( ) ->
      {
        val jpaChat = this.conversationMapper.toJpa( chat );
        val savedChat = this.jpaRepository.save( jpaChat );
        return this.conversationMapper.toDomain( savedChat );
      };
    val result = Try.of( saveConv )
                    .get( );
    return log.exit( result );

  }

  @Override
  public Conversation retrieveConversation( final UUID id )
    throws ChatRepositoryException{

    log.entry( id );
    val notFound = ( Supplier<ChatRepositoryException.NotFound> )( ) -> log.throwing( new ChatRepositoryException.NotFound( ) );
    val result = Option.ofOptional( this.jpaRepository.findById( id ) )
                       .map( this.conversationMapper::toDomain )
                       .getOrElseThrow( notFound );
    return log.exit( result );

  }

  @Override
  public Collection<Conversation> retrieveConversations( final Sort sorting )
    throws ChatRepositoryException{

    log.entry( sorting );
    val convsRetrieval = ( CheckedFunction0<List<JpaChat>> )( ) -> this.jpaRepository.findAll( sorting );
    val toEntity = ( Function<List<JpaChat>, List<Conversation>> )( jpaChats ) -> jpaChats.stream( )
                                                                                          .map( this.conversationMapper::toDomain )
                                                                                          .toList( );
    val result = Try.of( convsRetrieval )
                    .map( toEntity )
                    .get( );
    return log.exit( result );

  }

  @HandleDataAccessExceptions
  @Override
  public Conversation updateConversation( final Conversation chat )
    throws ChatRepositoryException{

    log.entry( chat );
    val convUpdate = ( CheckedFunction0<Conversation> )( ) ->
      {
        val existingChat = Option.ofOptional( this.jpaRepository.findById( chat.id( ) ) )
                                 .getOrElseThrow( ( ) -> log.throwing( new ChatRepositoryException.NotFound( ) ) );
        val updatedChat = this.conversationMapper.updateRootFromDomain( chat, existingChat ); //same instance actually
        val savedChat = this.jpaRepository.save( updatedChat );
        return this.conversationMapper.toDomain( savedChat );
      };
    val result = Try.of( convUpdate )
                    .get( );
    return log.exit( result );

  }

  @HandleDataAccessExceptions
  @Override
  public void deleteConversation( final UUID id )
    throws ChatRepositoryException{

    log.entry( id );
    val notFound = ( Supplier<ChatRepositoryException> )( ) -> log.throwing( new ChatRepositoryException.NotFound( ) );
    Option.ofOptional( this.jpaRepository.findById( id ) )
          .peek( this.jpaRepository::delete )
          .getOrElseThrow( notFound );
    log.exit( );

  }

  @Override
  public Conversation addRequestToChat( final Conversation chat, final ChatRequest chatRequest )
    throws ChatRepositoryException{

    return null;

  }

  @Override
  public Conversation addResponseToChat( final Conversation chat, final ChatResponse chatResponse )
    throws ChatRepositoryException{

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
  public Collection<Conversation> searchChatByContent( final String content )
    throws ChatRepositoryException{

    return null;

  }

}
