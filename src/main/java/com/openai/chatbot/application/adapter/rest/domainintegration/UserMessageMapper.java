package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( config = CentralConfig.class )
public interface UserMessageMapper{

  ChatRequest toEntity( UserMessageBody dto );

  UserMessageBody toDto( ChatRequest entity );

  @AfterMapping
  default void mapUserMessageContentToChatRequestMessage( final UserMessageBody dto, @MappingTarget final ChatRequest entity ){

    if( dto.content( ) != null ){
      ChatRequest.Message message = new ChatRequest.Message( ).content( dto.content( ) );
      entity.messages( ).add( message );
    }

  }

}