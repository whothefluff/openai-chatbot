package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestMessage;

/**
 * Conversions between {@link ChatRequest.Message} and {@link JpaChatRequestMessage}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = ChatRequestMessageFunctionCallMapper.class )
public interface ChatRequestMessageMapper{

  JpaChatRequestMessage toJpa( ChatRequest.Message domainEntity );

  ChatRequest.Message toDomain( JpaChatRequestMessage jpaEntity );

}
