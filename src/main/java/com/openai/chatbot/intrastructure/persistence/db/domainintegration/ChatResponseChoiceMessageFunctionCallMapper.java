package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoiceMessageFunctionCall;

/**
 * Conversions between {@link ChatResponse.Choice.Message.FunctionCall} and {@link JpaChatResponseChoiceMessageFunctionCall}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class )
public interface ChatResponseChoiceMessageFunctionCallMapper{

  JpaChatResponseChoiceMessageFunctionCall toJpa( ChatResponse.Choice.Message.FunctionCall domainEntity );

  ChatResponse.Choice.Message.FunctionCall toDomain( JpaChatResponseChoiceMessageFunctionCall jpaEntity );

}
