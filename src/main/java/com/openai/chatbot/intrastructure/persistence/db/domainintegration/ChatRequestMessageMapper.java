package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestMessage;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link ChatRequest.Message} and {@link JpaChatRequestMessage}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = ChatRequestMessageFunctionCallMapper.class )
public interface ChatRequestMessageMapper{

  JpaChatRequestMessage toJpa( ChatRequest.Message domainEntity );

  ChatRequest.Message toDomain( JpaChatRequestMessage jpaEntity );

}
