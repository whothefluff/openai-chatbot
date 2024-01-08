package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.port.secondary.ChatRepository;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ChatRequestMapper;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ConversationMapper;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

/**
 * Access information of the chats through a JPA repository
 */
@SuppressWarnings( "SerializableStoresNonSerializable" )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@Component
public class JpaChatRepository implements ChatRepository{

  JpaRepository<JpaChat, UUID> JpaRepository;
  ConversationMapper conversationMapper;
  ChatRequestMapper chatRequestMapper;

  @Override
  public Conversation saveNewConversation( final Conversation chat )
    throws ChatRepositoryException{

    log.entry( chat );
    val saveConv = ( CheckedFunction0<Conversation> )( ) ->
      {
        val jpaChat = this.conversationMapper.toJpa( chat );
        this.JpaRepository.save( jpaChat );
        return this.conversationMapper.toDomain( jpaChat );
      };
    val chatRepositoryException = ( Function<Throwable, ChatRepositoryException> )( e ) ->
      {
        log.catching( e );
        val exception = new ChatRepositoryException( e );
        log.throwing( exception );
        return exception;
      };
    val result = Try.of( saveConv ).getOrElseThrow( chatRepositoryException );
    log.exit( result );
    return result;

  }

  @Override
  public void deleteConversation( final Conversation chat )
    throws ChatRepositoryException{

    val jpaChat = this.conversationMapper.toJpa( chat );
    this.JpaRepository.delete( jpaChat );

  }

  @Override
  public Conversation updateConversation( final Conversation chat )
    throws ChatRepositoryException{

    return null;

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
