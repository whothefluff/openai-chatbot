package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequest;

/**
 * Conversions between {@link ChatRequest} and {@link JpaChatRequest}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = { ChatRequestMessageMapper.class, ChatRequestFunctionDefinitionMapper.class } )
public interface ChatRequestMapper{

  JpaChatRequest toJpa( ChatRequest domainEntity );

  ChatRequest toDomain( JpaChatRequest jpaEntity );

}
