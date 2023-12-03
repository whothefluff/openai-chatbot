package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Conversions between {@link Conversation} and {@link JpaChat}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING,
         uses = { ChatRequestMapper.class, ChatResponseMapper.class } )
public interface ConversationMapper{

  JpaChat toJpa( Conversation domainEntity );

  Conversation toDomain( JpaChat jpaEntity );

}
