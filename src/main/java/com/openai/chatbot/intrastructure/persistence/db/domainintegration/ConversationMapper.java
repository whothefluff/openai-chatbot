package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;

/**
 * Conversions between {@link Conversation} and {@link JpaChat}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class ,
         uses = { ChatRequestMapper.class, ChatResponseMapper.class } )
public interface ConversationMapper{

  JpaChat toJpa( Conversation domainEntity );

  Conversation toDomain( JpaChat jpaEntity );

}
