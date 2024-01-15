package com.openai.chatbot.application.adapter.rest.domainintegration;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for a user message
 */
@SuppressWarnings( { "ClassWithoutLogger", "ClassWithTooManyFields" } )
@Data
@EqualsAndHashCode
@ToString
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
public class UserMessageBody{

  private Integer id;
  private Instant createdAt;
  private Instant modifiedAt;
  private String model;
  private String functionCall;
  private BigDecimal temperature;
  private BigDecimal topP;
  private Integer n;
  private Boolean stream;
  private Integer maxTokens;
  private BigDecimal presencePenalty;
  private BigDecimal frequencyPenalty;
  private String logitBias;
  private String user;
  private String content;

}