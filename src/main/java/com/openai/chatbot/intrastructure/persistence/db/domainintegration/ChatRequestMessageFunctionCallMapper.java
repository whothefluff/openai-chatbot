package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestMessageFunctionCall;

/**
 * Conversions between {@link ChatRequest.Message.FunctionCall} and {@link JpaChatRequestMessageFunctionCall}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class )
public interface ChatRequestMessageFunctionCallMapper{

  JpaChatRequestMessageFunctionCall toJpa( ChatRequest.Message.FunctionCall domainEntity );

  ChatRequest.Message.FunctionCall toDomain( JpaChatRequestMessageFunctionCall jpaEntity );

}
