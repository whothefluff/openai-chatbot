package com.openai.chatbot.application.adapter.rest.domainintegration;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( config = CentralConfig.class )
public interface UserMessageMapper{

  ChatRequest toEntity( UserMessageBody dto );

  UserMessageBody toDto( ChatRequest entity );

  @AfterMapping
  default void mapUserMessageContentToChatRequestMessage( UserMessageBody dto, @MappingTarget ChatRequest entity ) {

    if( dto.content( ) != null ){
      ChatRequest.Message message = new ChatRequest.Message( ).content( dto.content( ) );
      entity.messages( ).add( message );
    }

  }

}