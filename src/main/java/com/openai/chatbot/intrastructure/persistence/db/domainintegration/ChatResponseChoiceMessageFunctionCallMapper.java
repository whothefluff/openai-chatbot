package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoiceMessageFunctionCall;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatResponse.Choice.Message.FunctionCall} and {@link JpaChatResponseChoiceMessageFunctionCall}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface ChatResponseChoiceMessageFunctionCallMapper{

  JpaChatResponseChoiceMessageFunctionCall toJpa( ChatResponse.Choice.Message.FunctionCall domainEntity );

  ChatResponse.Choice.Message.FunctionCall toDomain( JpaChatResponseChoiceMessageFunctionCall jpaEntity );

}
