package com.openai.chatbot.application.adapter.rest;

import com.openai.chatbot.application.adapter.rest.domainintegration.AssistantMessageBody;
import com.openai.chatbot.application.adapter.rest.domainintegration.HistoryMessage;
import com.openai.chatbot.application.adapter.rest.domainintegration.UserMessageBody;
import com.openai.chatbot.application.adapter.rest.domainintegration.UserMessageMapper;
import com.openai.chatbot.domain.port.primary.ChatService;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Controller for chat messages
 */
@SuppressWarnings( "UseOfConcreteClass" )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED,
                makeFinal = true )
@Accessors( chain = true,
            fluent = true )
@XSlf4j
@RestController
@RequestMapping( "/api/v1/conversation/{conversationId}" ) //NON-NLS
class ConversationMessageController{

  ChatService chatService;
  UserMessageMapper mapper;

  /**
   * Send a message to the assistant
   * @param userMessageBody the user message
   * @return the assistant message
   */
  @PostMapping( "/message" ) //NON-NLS
  public ResponseEntity<AssistantMessageBody> requestMessage( @PathVariable final UUID conversationId,
                                                              @RequestBody final UserMessageBody userMessageBody ){

    log.entry( conversationId, userMessageBody );
    // TODO maybe add sent msg + response
    /*val messageCreation = ( CheckedFunction0<AssistantMessageBody> )( ) ->
      {
        val msg = this.mapper.toEntity( userMessageBody );
        val response = this.chatService.addUserMessage( conversationId, msg );
        return this.mapper.toDto( response );
      };*/
    return new ResponseEntity<>( new AssistantMessageBody( ), HttpStatus.CREATED );

  }

  /**
   * Get all messages for a conversation
   * @param conversationId the conversation id
   * @return the messages
   */
  @GetMapping( "/messages" ) //NON-NLS
  public ResponseEntity<Collection<HistoryMessage>> getMessages( @PathVariable final UUID conversationId ){

    log.entry( conversationId );
    this.chatService.getConversationMessages( conversationId );
    val messages = ( Collection<HistoryMessage> )new ArrayList<HistoryMessage>( 0 );
    val response = ResponseEntity.ok( messages );
    return log.exit( response );

  }

  /**
   * Delete a message
   * @param conversationId the conversation id
   * @param messageId      the message id
   * @param messageType    the message type
   * @return HTTP code
   */
  @DeleteMapping( "/message/{messageId}/{messageType}" ) //NON-NLS
  public ResponseEntity<Void> deleteMessage( @PathVariable final UUID conversationId,
                                             @PathVariable final Integer messageId,
                                             @PathVariable final String messageType ){

    return ResponseEntity.noContent( ).build( );

  }

}
