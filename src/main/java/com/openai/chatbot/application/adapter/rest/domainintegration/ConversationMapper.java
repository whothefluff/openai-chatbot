package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( config = CentralConfig.class,
         implementationName = "RestConversationMapper" )
public interface ConversationMapper{

  ConversationBody toDto( Conversation entity );

  Conversation toEntity( ConversationBody dto );

}
