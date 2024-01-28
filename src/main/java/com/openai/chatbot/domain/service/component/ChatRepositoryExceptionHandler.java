package com.openai.chatbot.domain.service.component;

import com.openai.chatbot.domain.exception.ChatRepositoryException;
import com.openai.chatbot.domain.exception.ChatServiceException;
import io.vavr.Function1;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.XSlf4j;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * Contains advice to handle exceptions thrown by the repository
 */
@SuppressWarnings( "HardCodedStringLiteral" )
@XSlf4j
@FieldDefaults( level = AccessLevel.PRIVATE,
                makeFinal = true )
@Aspect
@Component
class ChatRepositoryExceptionHandler{

  @SuppressWarnings( { "PackageVisibleField", "NonConstantFieldWithUpperCaseName" } )
  static Map<Class<? extends Throwable>, Function1<Throwable, Throwable>> EXCEPTION_HANDLERS = HashMap.of(
    ChatRepositoryException.NotFound.class, ChatServiceException.NotFound::new,
    ChatRepositoryException.Conflict.class, ChatServiceException.Conflict::new,
    ChatRepositoryException.class, ChatServiceException::new
  );

  @Pointcut( "execution(* com.openai.chatbot.domain.service.*.*(..))" )
  public void repositoryPointcut( ){

  }

  /**
   * Translates {@link ChatRepositoryException} to the corresponding exception in the service
   * @param joinPoint the join point where the exception was thrown
   * @param wrapper   the exception thrown by the repository wrapped in an {@link UndeclaredThrowableException}
   * @throws ChatServiceException the exception thrown by the service
   */
  @AfterThrowing( pointcut = "repositoryPointcut( )",
                  throwing = "wrapper" )
  @SneakyThrows
  public void translate( final JoinPoint joinPoint, final UndeclaredThrowableException wrapper )
    throws ChatServiceException{

    log.entry( joinPoint, wrapper );
    val originalException = wrapper.getCause( );
    log.catching( originalException );
    throw log.throwing( EXCEPTION_HANDLERS.get( originalException.getClass( ) )
                                          .map( ( translator ) -> translator.apply( originalException ) )
                                          .getOrElseThrow( ( ) -> originalException ) );

  }

}
