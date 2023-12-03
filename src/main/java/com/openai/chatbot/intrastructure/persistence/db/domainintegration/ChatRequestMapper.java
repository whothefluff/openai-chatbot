package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatRequest} and {@link JpaChatRequest}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = { ChatRequestMessageMapper.class, ChatRequestFunctionDefinitionMapper.class } )
public interface ChatRequestMapper{

  JpaChatRequest toJpa( ChatRequest domainEntity );

  ChatRequest toDomain( JpaChatRequest jpaEntity );

}
