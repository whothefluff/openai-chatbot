package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatResponse;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponse;
import lombok.val;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversions between {@link ChatResponse} and {@link JpaChatResponse}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = { ChatResponseChoiceMapper.class, ChatResponseUsageMapper.class } )
public interface ChatResponseMapper{

  JpaChatResponse toJpa( ChatResponse domainEntity );

  ChatResponse toDomain( JpaChatResponse jpaEntity );

  @AfterMapping
  default void initializeChat( @MappingTarget final @NotNull JpaChatResponse jpaEntity ){

    if( jpaEntity.chat( ) == null ){
      val chat = new JpaChat( );
      Hibernate.initialize( chat );
      jpaEntity.chat( chat ); //avoid lazy fetching errors when mapping new chats (since it won't be loaded)
    }

  }

}
