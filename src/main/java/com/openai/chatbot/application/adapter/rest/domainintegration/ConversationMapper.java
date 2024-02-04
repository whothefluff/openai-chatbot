package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( config = CentralConfig.class,
         implementationName = "RestConversationMapper",
         builder = @Builder( disableBuilder = true ) )
public interface ConversationMapper{

  ConversationBody toDto( Conversation entity );

  Conversation toEntity( ConversationBody dto );

}
