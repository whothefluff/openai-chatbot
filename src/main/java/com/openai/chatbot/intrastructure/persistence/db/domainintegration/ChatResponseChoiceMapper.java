package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoice;

/**
 * Conversions between {@link ChatResponse.Choice} and {@link JpaChatResponseChoice}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = ChatResponseChoiceMessageMapper.class )
public interface ChatResponseChoiceMapper{

  JpaChatResponseChoice toJpa( ChatResponse.Choice domainEntity );

  ChatResponse.Choice toDomain( JpaChatResponseChoice jpaEntity );

}
