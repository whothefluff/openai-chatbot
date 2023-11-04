package com.openai.chatbot.domain.port.secondary.chatcompletions;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

//TODO implement in dtos? (step 4 or 6)
public interface Chat{

  UUID id( );

  Instant createdAt( );

  Instant modifiedAt( );

  String name( );

  Collection<ChatRequest> requests( );

  Collection<ChatResponse> responses( );

}