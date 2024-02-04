package com.openai.chatbot.domain.service.component;

import com.openai.chatbot.domain.exception.ChatServiceException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pointcut for {@link ChatRepositoryExceptionHandler}. Indicates that relevant exceptions thrown by the annotated method should be translated into
 * specific subtypes of {@link ChatServiceException}.
 */
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface HandleChatRepositoryExceptions{

}
