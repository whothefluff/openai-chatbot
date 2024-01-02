package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponse;

/**
 * Conversions between {@link ChatResponse} and {@link JpaChatResponse}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = { ChatResponseChoiceMapper.class, ChatResponseUsageMapper.class } )
public interface ChatResponseMapper{

  JpaChatResponse toJpa( ChatResponse domainEntity );

  ChatResponse toDomain( JpaChatResponse jpaEntity );

}
