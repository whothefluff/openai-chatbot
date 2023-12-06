package com.openai.chatbot.intrastructure.configuration;

import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@MapperConfig( componentModel = MappingConstants.ComponentModel.SPRING,
               unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface CentralConfig{

}
