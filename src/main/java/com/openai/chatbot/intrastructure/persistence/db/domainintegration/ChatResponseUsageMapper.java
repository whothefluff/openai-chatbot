package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseUsage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatResponse.Usage} and {@link JpaChatResponseUsage}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface ChatResponseUsageMapper{

  JpaChatResponseUsage toJpa( ChatResponse.Usage domainEntity );

  ChatResponse.Usage toDomain( JpaChatResponseUsage jpaEntity );

}
