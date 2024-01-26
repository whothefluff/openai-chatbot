package com.openai.chatbot.application.adapter.rest;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationMapper;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationStarterBody;
import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.domain.port.primary.ChatService;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import lombok.val;
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
@FieldDefaults( level = AccessLevel.PROTECTED,
                makeFinal = true )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@RestController
@RequestMapping( "/api/v1" ) //NON-NLS
class ConversationController{

  private static final String ID_PATH = "/{id}"; //NON-NLS
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
    val conversationCreation = ( CheckedFunction0<ConversationBody> )( ) ->
      {
        val createdConversation = this.chatService.startConversation( conversationStarterBody.name( ), conversationStarterBody.systemMessage( ) );
        return this.mapper.toDto( createdConversation );
      };
    val responseReturn = ( Function<ConversationBody, ResponseEntity<?>> )( conversationBody ) ->
      {
        val location = ServletUriComponentsBuilder.fromCurrentRequest( ).path( ID_PATH ).buildAndExpand( conversationBody.id( ) ).toUri( );
        return ResponseEntity.created( location ).body( conversationBody );
      };
    val response = Try.of( conversationCreation )
                      .map( responseReturn )
                      .get( );
    return log.exit( response );

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
  @GetMapping( "/conversation" + ID_PATH ) //NON-NLS
  public ResponseEntity<?> getConversation( @PathVariable final UUID id ){

    log.entry( id );
    val convRetrieval = ( CheckedFunction0<Conversation> )( ) -> this.chatService.getConversation( id );
    val returnResponse = ( Function<Conversation, ResponseEntity<?>> )( conversation ) ->
      {
        val conversationBody = this.mapper.toDto( conversation );
        return ResponseEntity.ok( conversationBody );
      };
    val result = Try.of( convRetrieval )
                    .map( returnResponse )
                    .get( );
    return log.exit( result );

  }

  /**
   * Update a conversation
   * @param id               the conversation to update
   * @param conversationBody the new information
   * @return the updated conversation
   */
  @PutMapping( "/conversation" + ID_PATH ) //NON-NLS
  public ResponseEntity<ConversationBody> updateConversation( @PathVariable final UUID id, @RequestBody final ConversationBody conversationBody ){
    //this.chatService.updateChat( id, conversationBody );
    return null;

  }

  /**
   * Delete a conversation
   * @param id the conversation to delete
   * @return 204 No Content if successful, 404 Not Found if not found, 500 Internal Server Error if error during deletion
   */
  @DeleteMapping( "/conversation" + ID_PATH ) //NON-NLS
  public ResponseEntity<?> deleteConversation( @PathVariable final UUID id ){

    log.entry( id );
    val conversationRemoval = ( CheckedRunnable )( ) -> this.chatService.deleteConversation( id );
    val responseReturn = ( Function<Void, ResponseEntity<?>> )( v ) -> ResponseEntity.noContent( ).build( );
    val response = Try.run( conversationRemoval )
                      .map( responseReturn )
                      .get( );
    return log.exit( response );

  }

  /**
   * Search conversations by content
   * @param searchString the search string
   * @return the conversations
   */
  @GetMapping( "/searchConversations" ) //NON-NLS
  public ResponseEntity<Collection<ConversationBody>> searchByContent( @RequestParam final String searchString ){

    return null; //TODO

  }

}
