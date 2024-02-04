package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.Conversation;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat_;
import org.mapstruct.*;

/**
 * Conversions between {@link Conversation} and {@link JpaChat}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = { ChatRequestMapper.class, ChatResponseMapper.class },
         builder = @Builder( disableBuilder = true ) )
public interface ConversationMapper{

  JpaChat toJpa( Conversation domainEntity );

  Conversation toDomain( JpaChat jpaEntity );

  @SuppressWarnings( "HardCodedStringLiteral" )
  @Mapping( source = "name",
            target = JpaChat_.NAME )
  @BeanMapping( ignoreByDefault = true )
  JpaChat updateRootFromDomain( Conversation domainEntity, @MappingTarget JpaChat jpaEntity );

}
