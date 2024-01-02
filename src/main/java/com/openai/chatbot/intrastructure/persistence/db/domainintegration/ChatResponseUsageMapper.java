package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseUsage;

/**
 * Conversions between {@link ChatResponse.Usage} and {@link JpaChatResponseUsage}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class )
public interface ChatResponseUsageMapper{

  JpaChatResponseUsage toJpa( ChatResponse.Usage domainEntity );

  ChatResponse.Usage toDomain( JpaChatResponseUsage jpaEntity );

}
