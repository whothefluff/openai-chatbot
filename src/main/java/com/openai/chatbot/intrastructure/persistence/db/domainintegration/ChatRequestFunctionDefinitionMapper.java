package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import org.mapstruct.Mapper;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestFunctionDefinition;

/**
 * Conversions between {@link ChatRequest.FunctionDefinition} and {@link JpaChatRequestFunctionDefinition}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class )
public interface ChatRequestFunctionDefinitionMapper{

  JpaChatRequestFunctionDefinition toJpa( ChatRequest.FunctionDefinition domainEntity );

  ChatRequest.FunctionDefinition toDomain( JpaChatRequestFunctionDefinition jpaEntity );

}
