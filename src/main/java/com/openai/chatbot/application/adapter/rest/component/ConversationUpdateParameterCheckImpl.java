package com.openai.chatbot.application.adapter.rest.component;

import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationBody;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@SuppressWarnings( "MissingJavadoc" )
@XSlf4j
@Component
public class ConversationUpdateParameterCheckImpl implements ConversationUpdateParameterCheck{

  @Override
  public void validate( final UUID id, final ConversationBody conversationBody ){

    log.entry( id, conversationBody );
    if( conversationBody.id( ) == null || conversationBody.id( ) == id ){
      log.exit( );
    }
    else{
      throw log.throwing( new ResponseStatusException( HttpStatus.BAD_REQUEST, "Path ID does not match ID in request body" ) );
    }

  }

}
