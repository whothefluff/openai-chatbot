package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.ChatMessageRole;
import com.openai.chatbot.domain.entity.ChatRequest;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
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