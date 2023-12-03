package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatResponse} and {@link JpaChatResponse}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = { ChatResponseChoiceMapper.class, ChatResponseUsageMapper.class } )
public interface ChatResponseMapper{

  JpaChatResponse toJpa( ChatResponse domainEntity );

  ChatResponse toDomain( JpaChatResponse jpaEntity );

}
