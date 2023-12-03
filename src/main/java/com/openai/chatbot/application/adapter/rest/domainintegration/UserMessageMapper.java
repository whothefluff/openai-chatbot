package com.openai.chatbot.application.adapter.rest.domainintegration;

import com.openai.chatbot.domain.entity.ChatRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@SuppressWarnings( { "MissingJavadoc", "UseOfConcreteClass", "unused" } )
@Mapper( unmappedTargetPolicy = ReportingPolicy.IGNORE,
         componentModel = MappingConstants.ComponentModel.SPRING )
public interface UserMessageMapper{

  ChatRequest toEntity( UserMessageBody dto );

  UserMessageBody toDto( ChatRequest entity );

}