package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatResponse.Choice} and {@link JpaChatResponseChoice}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = ChatResponseChoiceMessageMapper.class )
public interface ChatResponseChoiceMapper{

  JpaChatResponseChoice toJpa( ChatResponse.Choice domainEntity );

  ChatResponse.Choice toDomain( JpaChatResponseChoice jpaEntity );

}
