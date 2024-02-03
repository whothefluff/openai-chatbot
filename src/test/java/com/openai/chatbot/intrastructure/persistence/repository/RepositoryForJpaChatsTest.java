package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ChatRequestMapper;
import com.openai.chatbot.intrastructure.persistence.db.domainintegration.ConversationMapper;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.val;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class RepositoryForJpaChatsTest{

  @Test
  void saveNewConversation_ConversationDoesNotExist_SavesAndReturnsConversation( )
    throws ChatRepositoryException{
    // Arrange
    val newJpaConv = new JpaChat( );
    val newConv = new Conversation( );
    val repository = new RepositoryForJpaChats( new JpaChatRepositoryDouble( ),
                                                new ConversationMapperStub( newConv, newJpaConv ),
                                                new ChatRequestMapperDummy( ) );
    // Act
    val savedConv = repository.saveNewConversation( newConv );
    // Assert
    assertThat( savedConv ).isEqualTo( newConv );

  }

  @Test
  void saveNewConversation_ErrorOccurs_ThrowsExceptionAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( "some error" );
    val newConv = new Conversation( );
    val repository = new RepositoryForJpaChats( new ConvSaveExceptionStub( someError ),
                                                new ConversationMapperDummy( ),
                                                new ChatRequestMapperDummy( ) );
    val convInsert = ( ThrowingCallable )( ) -> repository.saveNewConversation( newConv );
    // Act & Assert
    assertThatThrownBy( convInsert ).isEqualTo( someError );

  }

  @Test
  void retrieveConversation_ConversationExists_ReturnsConversation( )
    throws ChatRepositoryException{
    // Arrange
    val conv = new Conversation( );
    val repository = new RepositoryForJpaChats( new SuccessfulConvRetrievalStub( new JpaChat( ) ),
                                                new ConversationToDomainStub( conv ),
                                                new ChatRequestMapperDummy( ) );
    // Act
    val retrievedConv = repository.retrieveConversation( null );
    // Assert
    assertThat( retrievedConv ).isEqualTo( conv );

  }

  @Test
  void retrieveConversation_ConversationDoesNotExist_ThrowsNotFound( )
    throws ChatRepositoryException{
    // Arrange
    val repository = new RepositoryForJpaChats( new UnsuccessfulConvRetrievalStub( ), new ConversationMapperDummy( ), new ChatRequestMapperDummy( ) );
    val convRetrieval = ( ThrowingCallable )( ) -> repository.retrieveConversation( null );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.NotFound.class ).isThrownBy( convRetrieval );

  }

  @Test
  void retrieveConversations_ConversationsFound_ConversationsReturned( )
    throws ChatRepositoryException{
    // Arrange
    val convs = List.of( new Conversation( ).id( UUID.randomUUID( ) ),
                         new Conversation( ).id( UUID.randomUUID( ) ) );
    val mappedConvs = convs.stream( )
                           .map( conv -> new JpaChat( ).id( conv.id( ) ) )
                           .toList( );
    val repository = new RepositoryForJpaChats( new SuccessfulConvsRetrievalStub( mappedConvs ),
                                                new ConversationsToDomainStub( convs.stream( )
                                                                                    .collect( Collectors.toMap( Conversation::id,
                                                                                                                Function.identity( ) ) ) ),
                                                new ChatRequestMapperDummy( ) );
    // Act
    val retrievedConvs = repository.retrieveConversations( null );
    // Assert
    assertThat( retrievedConvs ).isEqualTo( convs );

  }

  @Test
  void retrieveConversations_NoConversations_EmptyCollectionReturned( )
    throws ChatRepositoryException{
    // Arrange
    final List<JpaChat> noConvs = Collections.emptyList( );
    val repository = new RepositoryForJpaChats( new SuccessfulConvsRetrievalStub( noConvs ),
                                                new ConversationMapperDummy( ),
                                                new ChatRequestMapperDummy( ) );
    // Act
    val retrievedConvs = repository.retrieveConversations( null );
    // Assert
    assertThat( retrievedConvs ).isEmpty( );

  }

  @Test
  void updateConversation_ConversationExists_SavesAndReturnsConversation( )
    throws ChatRepositoryException{
    // Arrange
    val conv = new Conversation( ).id( UUID.randomUUID( ) );
    val jpaConv = new JpaChat( );
    val repository = new RepositoryForJpaChats( new SuccessfulConvUpdateStub( jpaConv ),
                                                new ConversationMapperStub( conv, jpaConv ),
                                                new ChatRequestMapperDummy( ) );
    // Act
    val savedConv = repository.updateConversation( conv );
    // Assert
    assertThat( savedConv ).isEqualTo( conv );

  }

  @Test
  void updateConversation_ConversationDoesNotExist_ThrowsNotFound( )
    throws ChatRepositoryException{
    // Arrange
    val conv = new Conversation( ).id( UUID.randomUUID( ) );
    val repository = new RepositoryForJpaChats( new JpaChatRepositoryDouble( ), new ConversationMapperDummy( ), new ChatRequestMapperDummy( ) );
    val convUpdate = ( ThrowingCallable )( ) -> repository.updateConversation( conv );
    // Act & Assert
    assertThatExceptionOfType( ChatRepositoryException.NotFound.class ).isThrownBy( convUpdate );

  }

  @Test
  void updateConversation_ErrorOccurs_ThrowsAsIs( )
    throws ChatRepositoryException{
    // Arrange
    val someError = new RuntimeException( "some error" );
    val conv = new Conversation( ).id( UUID.randomUUID( ) );
    val repository = new RepositoryForJpaChats( new UnsuccessfulConvUpdateStub( someError ),
                                                new ConversationMapperDummy( ),
                                                new ChatRequestMapperDummy( ) );
    val convUpdate = ( ThrowingCallable )( ) -> repository.updateConversation( conv );
    // Act & Assert
    assertThatThrownBy( convUpdate ).isEqualTo( someError );

  }
  // ##############################

  @SuppressWarnings( { "ReturnOfNull", "NullableProblems" } )
  private static class JpaChatRepositoryDouble implements JpaChatRepository{

    @Override
    public void flush( ){

    }

    @Override
    public <S extends JpaChat> S saveAndFlush( final S entity ){

      return null;

    }

    @Override
    public <S extends JpaChat> List<S> saveAllAndFlush( final Iterable<S> entities ){

      return null;

    }

    @Override
    public void deleteAllInBatch( final Iterable<JpaChat> entities ){

    }

    @Override
    public void deleteAllByIdInBatch( final Iterable<UUID> uuids ){

    }

    @Override
    public void deleteAllInBatch( ){

    }

    @Override
    public JpaChat getOne( final UUID uuid ){

      return null;

    }

    @Override
    public JpaChat getById( final UUID uuid ){

      return null;

    }

    @Override
    public JpaChat getReferenceById( final UUID uuid ){

      return null;

    }

    @Override
    public <S extends JpaChat> List<S> findAll( final Example<S> example ){

      return null;

    }

    @Override
    public <S extends JpaChat> List<S> findAll( final Example<S> example, final Sort sort ){

      return null;

    }

    @Override
    public <S extends JpaChat> Optional<S> findOne( final Example<S> example ){

      return Optional.empty( );

    }

    @Override
    public <S extends JpaChat> Page<S> findAll( final Example<S> example, final Pageable pageable ){

      return null;

    }

    @Override
    public <S extends JpaChat> long count( final Example<S> example ){

      return 0L;

    }

    @Override
    public <S extends JpaChat> boolean exists( final Example<S> example ){

      return false;

    }

    @Override
    public <S extends JpaChat, R> R findBy( final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction ){

      return null;

    }

    @Override
    public <S extends JpaChat> S save( final S entity ){

      return null;

    }

    @Override
    public Optional<JpaChat> findById( final UUID uuid ){

      return Optional.empty( );

    }

    @Override
    public boolean existsById( final UUID uuid ){

      return false;

    }

    @Override
    public long count( ){

      return 0L;

    }

    @Override
    public void deleteById( final UUID uuid ){

    }

    @Override
    public void delete( final JpaChat entity ){

    }

    @Override
    public void deleteAllById( final Iterable<? extends UUID> uuids ){

    }

    @Override
    public void deleteAll( final Iterable<? extends JpaChat> entities ){

    }

    @Override
    public void deleteAll( ){

    }

    @Override
    public <S extends JpaChat> List<S> saveAll( final Iterable<S> entities ){

      return null;

    }

    @Override
    public List<JpaChat> findAll( ){

      return null;

    }

    @Override
    public List<JpaChat> findAllById( final Iterable<UUID> uuids ){

      return null;

    }

    @Override
    public List<JpaChat> findAll( final Sort sort ){

      return null;

    }

    @Override
    public Page<JpaChat> findAll( final Pageable pageable ){

      return null;

    }

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class SuccessfulConvRetrievalStub extends JpaChatRepositoryDouble{

    final JpaChat jpaChat;

    @Override
    public Optional<JpaChat> findById( final UUID id ){

      return Optional.of( this.jpaChat );

    }

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class SuccessfulConvsRetrievalStub extends JpaChatRepositoryDouble{

    final List<JpaChat> chats;

    @Override
    public List<JpaChat> findAll( final Sort sorting ){

      return Collections.unmodifiableList( this.chats );

    }

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class SuccessfulConvUpdateStub extends JpaChatRepositoryDouble{

    final JpaChat jpaChat;

    @SuppressWarnings( "unchecked" )
    @Override
    public <S extends JpaChat> S save( final S entity ){

      return ( S )this.jpaChat;

    }

    @Override
    public Optional<JpaChat> findById( final UUID uuid ){

      return Optional.of( this.jpaChat );

    }

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class UnsuccessfulConvUpdateStub extends JpaChatRepositoryDouble{

    final Exception exception;

    @Override
    @SneakyThrows
    public <S extends JpaChat> S save( final S entity ){

      throw this.exception;

    }

    @Override
    public Optional<JpaChat> findById( final UUID uuid ){

      return Optional.of( new JpaChat( ) );

    }

  }

  private static class UnsuccessfulConvRetrievalStub extends JpaChatRepositoryDouble{

  }

  @Data
  @EqualsAndHashCode( callSuper = true )
  private static class ConvSaveExceptionStub extends JpaChatRepositoryDouble{

    final Exception exception;

    @SneakyThrows
    @Override
    public <S extends JpaChat> S save( final S entity ){

      throw this.exception;

    }

  }

  private static class ConversationMapperDouble implements ConversationMapper{

    @Override
    public @Nullable JpaChat toJpa( final Conversation conversation ){

      return null;

    }

    @Override
    public @Nullable Conversation toDomain( final JpaChat jpaChat ){

      return null;

    }

  }

  private static class ChatRequestMapperDouble implements ChatRequestMapper{

    @Override
    public @Nullable JpaChatRequest toJpa( final ChatRequest domainEntity ){

      return null;

    }

    @Override
    public @Nullable ChatRequest toDomain( final JpaChatRequest jpaEntity ){

      return null;

    }

  }

  private static class ConversationMapperDummy extends ConversationMapperDouble{

  }

  private static class ChatRequestMapperDummy extends ChatRequestMapperDouble{

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationToDomainStub extends ConversationMapperDouble{

    final Conversation conversation;

    @Override
    public @Nullable Conversation toDomain( final JpaChat jpaChat ){

      return this.conversation;

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationsToDomainStub extends ConversationMapperDouble{

    final Map<UUID, Conversation> conversations;

    @Override
    public @Nullable Conversation toDomain( final JpaChat jpaChat ){

      return this.conversations.get( jpaChat.id( ) );

    }

  }

  @EqualsAndHashCode( callSuper = true )
  @Data
  private static class ConversationMapperStub extends ConversationMapperDouble{

    final Conversation conversation;
    final JpaChat jpaChat;

    @Override
    public @Nullable JpaChat toJpa( final Conversation conversation ){

      return this.jpaChat;

    }

    @Override
    public @Nullable Conversation toDomain( final JpaChat jpaChat ){

      return this.conversation;

    }

  }

}
