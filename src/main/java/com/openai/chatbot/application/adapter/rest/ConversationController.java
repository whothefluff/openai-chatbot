package com.openai.chatbot.application.adapter.rest;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationMapper;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationStarterBody;
import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.port.primary.ChatService;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

/**
 * Controller for chats
 */
@SuppressWarnings( { "UseOfConcreteClass", "SerializableStoresNonSerializable" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@RestController
@RequestMapping( "/api/v1" ) //NON-NLS
class ConversationController{

  ChatService chatService;
  ConversationMapper mapper;

  /**
   * Start a conversation
   * @param conversationStarterBody the conversation starter
   * @return the conversation
   */
  @PostMapping( "/conversation" ) //NON-NLS
  public ResponseEntity<?> createConversation( @RequestBody final ConversationStarterBody conversationStarterBody ){

    log.entry( conversationStarterBody );
    val getNewConversation = ( CheckedFunction0<ConversationBody> )( ) ->
      {
        val createdConversation = this.chatService.startConversation( conversationStarterBody.name( ), conversationStarterBody.systemMessage( ) );
        return this.mapper.toDto( createdConversation );
      };
    val returnResponse = ( Function<ConversationBody, ResponseEntity<?>> )( conversationBody ) ->
      {
        val location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( "/{id}" ).buildAndExpand( conversationBody.id( ) ).toUri( ); //NON-NLS
        return ResponseEntity.created( location ).body( conversationBody );
      };
    val returnServiceError = ( Function<ChatServiceException, ResponseEntity<String>> )( e ) ->
      {
        log.catching( e );
        return ResponseEntity.badRequest( ).body( e.getMessage( ) );
      };
    val internalError = ( Function<Throwable, ResponseEntity<?>> )( e ) ->
      {
        log.catching( e );
        return ResponseEntity.internalServerError( ).body( e.getMessage( ) );
      };
    val response = Try.of( getNewConversation )
                      .map( returnResponse )
                      .recover( ChatServiceException.class, returnServiceError )
                      .getOrElseGet( internalError );
    log.exit( response );
    return response; //TODO test method

  }

  /**
   * @return all conversations
   */
  @GetMapping( "/conversations" ) //NON-NLS
  public ResponseEntity<Collection<ConversationBody>> getConversations( ){
    //this.chatService.getChats( );
    return null;

  }

  /**
   * Get a conversation
   * @param id the conversation id
   * @return the conversation
   */
  @GetMapping( "/conversation/{id}" ) //NON-NLS
  public ResponseEntity<ConversationBody> getConversation( @PathVariable final UUID id ){
    //this.chatService.getChat( id );
    return null;

  }

  /**
   * Update a conversation
   * @param id               the conversation to update
   * @param conversationBody the new information
   * @return the updated conversation
   */
  @PutMapping( "/conversation/{id}" ) //NON-NLS
  public ResponseEntity<ConversationBody> updateConversation( @PathVariable final UUID id, @RequestBody final ConversationBody conversationBody ){
    //this.chatService.updateChat( id, conversationBody );
    return null;

  }

  /**
   * Delete a conversation
   * @param id the conversation to delete
   * @return 204 No Content if successful, 404 Not Found if not found, 500 Internal Server Error if error during deletion
   */
  @DeleteMapping( "/conversation/{id}" ) //NON-NLS
  public ResponseEntity<?> deleteConversation( @PathVariable final UUID id ){
    //chatService.deleteConversation( null );
    val deletionSuccessful = true;
    val optionalTest = java.util.Optional.of( "test" ); // NON-NLS
    if( deletionSuccessful ){
      return ResponseEntity.noContent( ).build( );
    }
    else{  //chat not found, error during deletion, ...
      return ResponseEntity.notFound( ).build( );
    }

  }

  /**
   * Search conversations by content
   * @param searchString the search string
   * @return the conversations
   */
  @GetMapping( "/searchConversations" ) //NON-NLS
  public ResponseEntity<Collection<ConversationBody>> searchByContent( @RequestParam final String searchString ){

    return ResponseEntity.ok( null );

  }

}
