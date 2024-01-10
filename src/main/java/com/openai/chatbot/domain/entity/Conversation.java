package com.openai.chatbot.domain.entity;

import static com.openai.chatbot.domain.entity.ChatMessageRole.system;

import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.function.Supplier;

import org.springframework.util.StringUtils;

import io.vavr.Function2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;

/**
 * A conversation
 */
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

  public Conversation addRequest( ChatRequest request ){

    log.entry( request );
    this.requests.add( request );
    return log.exit( this );
    
  }

  public Conversation addResponse( ChatResponse response ){

    log.entry( response );
    this.responses.add( response );
    return log.exit( this );
    
  }

  public static InitialStateBuilder initialStateBuilder( ){

    return log.exit( new InitialStateBuilder( ) );

  }

  @FieldDefaults( level = AccessLevel.PRIVATE )
  @Accessors( chain = true,
              fluent = true )
  @Setter
  @ToString
  public static class InitialStateBuilder {

    String name;
    String systemMessage;
    private Supplier<IllegalArgumentException> illegalArgs;

    public Conversation build( ){

      log.entry( );
      val nameValidation = Option.of( name ).filter( StringUtils::hasText ).toValidation( "Name must not be empty" );
      val systemMessageValidation = Option.of( systemMessage ).filter( StringUtils::hasText ).toValidation( "System message must not be empty" );
      val returnConversation = ( Function2<String, String, Conversation> )( name, systemMessage ) -> {
        val requestMsg = new ChatRequest.Message( ).role( system ).content( systemMessage );
        val request = new ChatRequest( ).addMessage( requestMsg );
        val conv = new Conversation( ).name( name ).addRequest( request );
        return log.exit( conv );
      };
      val validation = Validation.combine( nameValidation, systemMessageValidation ).ap( returnConversation );
      illegalArgs = ( ) -> {
        val error = new IllegalArgumentException( validation.getError( ).mkString( ", " ) );
        return log.throwing( error );
      };
      return Try.of( validation::get ).getOrElseThrow( illegalArgs );

    }
  
  }

}