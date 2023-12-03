package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestMessageFunctionCall;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatRequest.Message.FunctionCall} and {@link JpaChatRequestMessageFunctionCall}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface ChatRequestMessageFunctionCallMapper{

  JpaChatRequestMessageFunctionCall toJpa( ChatRequest.Message.FunctionCall domainEntity );

  ChatRequest.Message.FunctionCall toDomain( JpaChatRequestMessageFunctionCall jpaEntity );

}
