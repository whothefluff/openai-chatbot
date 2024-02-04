package com.openai.chatbot.domain.service.component;

import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.exception.ChatServiceException;
import io.vavr.Function1;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.XSlf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.UndeclaredThrowableException;

@SuppressWarnings( "HardCodedStringLiteral" )
@XSlf4j
@Aspect
@Component
class ChatRepositoryExceptionHandler{

  private static final Map<Class<? extends Throwable>, Function1<Throwable, ChatServiceException>> EXCEPTION_HANDLERS = HashMap.of(
    ChatRepositoryException.NotFound.class, ChatServiceException.NotFound::new,
    ChatRepositoryException.Conflict.class, ChatServiceException.Conflict::new,
    ChatRepositoryException.class, ChatServiceException::new
  );
  
  @SuppressWarnings( "RedundantThrows" )
  @AfterThrowing( pointcut = "@annotation( HandleChatRepositoryExceptions )",
                  throwing = "wrapper" )
  @SneakyThrows
  void translate( final JoinPoint joinPoint, final UndeclaredThrowableException wrapper )
    throws ChatServiceException{

    log.entry( joinPoint, wrapper );
    val originalException = wrapper.getCause( );
    log.catching( originalException );
    throw log.throwing( EXCEPTION_HANDLERS.get( originalException.getClass( ) )
                                          .map( ( translator ) -> translator.apply( originalException ) )
                                          .getOrElseThrow( ( ) -> originalException ) );

  }

}
