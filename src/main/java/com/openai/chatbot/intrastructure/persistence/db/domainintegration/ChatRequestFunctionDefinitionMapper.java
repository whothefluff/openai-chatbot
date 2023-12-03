package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestFunctionDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatRequest.FunctionDefinition} and {@link JpaChatRequestFunctionDefinition}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface ChatRequestFunctionDefinitionMapper{

  JpaChatRequestFunctionDefinition toJpa( ChatRequest.FunctionDefinition domainEntity );

  ChatRequest.FunctionDefinition toDomain( JpaChatRequestFunctionDefinition jpaEntity );

}
