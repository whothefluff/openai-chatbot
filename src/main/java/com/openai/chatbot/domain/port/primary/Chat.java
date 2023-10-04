package com.openai.chatbot.domain.port.primary;

import java.time.Instant;
import java.util.UUID;

public interface Chat{

  UUID id( );

  Instant startTime( );

  Instant endTime( );

  String name( );

}
