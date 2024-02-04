package com.openai.chatbot.intrastructure.persistence.db.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import com.openai.chatbot.intrastructure.configuration.CentralConfig;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequest;
import lombok.val;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Conversions between {@link ChatRequest} and {@link JpaChatRequest}.
 */
@SuppressWarnings( "MissingJavadoc" )
@Mapper( config = CentralConfig.class,
         uses = { ChatRequestMessageMapper.class, ChatRequestFunctionDefinitionMapper.class } )
public interface ChatRequestMapper{

  JpaChatRequest toJpa( ChatRequest domainEntity );

  ChatRequest toDomain( JpaChatRequest jpaEntity );

  @AfterMapping
  default void initializeChat( @MappingTarget final @NotNull JpaChatRequest jpaEntity ){
    //avoid lazy fetching errors when mapping new chats (since it won't be loaded)
    if( jpaEntity.chat( ) == null ){
      val chat = new JpaChat( );
      jpaEntity.chat( chat );
    }
    Hibernate.initialize( jpaEntity.chat( ) );

  }

}
