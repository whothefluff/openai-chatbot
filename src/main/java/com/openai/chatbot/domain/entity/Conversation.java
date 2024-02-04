package com.openai.chatbot.domain.entity;

import io.vavr.Function3;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.function.Supplier;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;

/**
 * A conversation
 */
@SuppressWarnings( { "CollectionWithoutInitialCapacity", "UseOfConcreteClass" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
public class Conversation{

  UUID id;
  Instant createdAt;
  Instant modifiedAt;
  String name;
  Collection<ChatRequest> requests = new LinkedHashSet<>( );
  Collection<ChatResponse> responses = new LinkedHashSet<>( );

  /**
   * @return a new {@link InitialStateBuilder} instance
   */
  public static InitialStateBuilder initialStateBuilder( ){

    return log.exit( new InitialStateBuilder( ) );

  }

  @SuppressWarnings( "MissingJavadoc" )
  public Conversation addRequest( final ChatRequest request ){

    log.entry( request );
    this.requests.add( request );
    return log.exit( this );

  }

  @SuppressWarnings( "MissingJavadoc" )
  public Conversation addResponse( final ChatResponse response ){

    log.entry( response );
    this.responses.add( response );
    return log.exit( this );

  }

  /**
   * Helps create a valid initial {@link Conversation} instance
   */
  @SuppressWarnings( "PublicConstructor" )
  @FieldDefaults( level = AccessLevel.PRIVATE )
  @Accessors( chain = true,
              fluent = true )
  @Setter
  @ToString
  public static class InitialStateBuilder{

    String name;
    String model;
    String systemMessage;

    /**
     * @return a valid {@link Conversation} instance
     * @throws IllegalArgumentException if the instance is invalid
     */
    @SuppressWarnings( "HardCodedStringLiteral" )
    public Conversation build( ){

      log.entry( );
      val nameValidation = Option.of( this.name ).filter( StringUtils::hasText ).toValidation( "Name must not be empty" );
      val modelValidation = Option.of( this.model ).filter( StringUtils::hasText ).toValidation( "Model must not be empty" );
      val systemMessageValidation = Option.of( this.systemMessage ).filter( StringUtils::hasText ).toValidation( "System message must not be empty" );
      val returnConversation = ( Function3<String, String, String, Conversation> )( name, model, systemMessage ) ->
        {
          val requestMsg = new ChatRequest.Message( ).role( system ).content( systemMessage );
          val request = new ChatRequest( ).model( model ).addMessage( requestMsg );
          val conv = new Conversation( ).name( name ).addRequest( request );
          return log.exit( conv );
        };
      val validation = Validation.combine( nameValidation, modelValidation, systemMessageValidation )
                                 .ap( returnConversation );
      val illegalArgs = ( Supplier<IllegalArgumentException> )( ) ->
        {
          val error = new IllegalArgumentException( validation.getError( ).mkString( ", " ) );
          return log.throwing( error );
        };
      return Try.of( validation::get )
                .getOrElseThrow( illegalArgs );

    }

  }

}