package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface ConversationMapper{

  ConversationBody toDto( Conversation entity );

  Conversation toEntity( ConversationBody dto );

}
