package com.openai.chatbot.intrastructure.persistence.repository.component;

import com.openai.chatbot.domain.exception.ChatRepositoryException;
import io.vavr.Function1;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.XSlf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@SuppressWarnings( "HardCodedStringLiteral" )
@XSlf4j
@Aspect
@Component
class DataAccessExceptionsHandler{

  private static final Map<Class<? extends Throwable>, Function1<Throwable, ChatRepositoryException>> EXCEPTION_HANDLERS = HashMap.of(
    DataIntegrityViolationException.class, ChatRepositoryException.Conflict::new
  );

  @SuppressWarnings( "RedundantThrows" )
  @AfterThrowing( pointcut = "@annotation( HandleDataAccessExceptions )",
                  throwing = "originalException" )
  @SneakyThrows
  void translate( final JoinPoint joinPoint, final DataAccessException originalException )
    throws ChatRepositoryException{

    log.entry( joinPoint, originalException );
    log.catching( originalException );
    throw log.throwing( EXCEPTION_HANDLERS.filter( ( clazz, handler ) -> clazz.isAssignableFrom( originalException.getClass( ) ) )
                                          .mapValues( ( handler ) -> handler.apply( originalException ) )
                                          .getOrElseThrow( ( ) -> originalException )._2 );

  }

}
