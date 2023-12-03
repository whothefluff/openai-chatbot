package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseChoiceMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatResponse.Choice.Message} and {@link JpaChatResponseChoiceMessage}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = ChatResponseChoiceMessageFunctionCallMapper.class )
public interface ChatResponseChoiceMessageMapper{

  JpaChatResponseChoiceMessage toJpa( ChatResponse.Choice.Message domainEntity );

  ChatResponse.Choice.Message toDomain( JpaChatResponseChoiceMessage jpaEntity );

}
