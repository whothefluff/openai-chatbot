package com.openai.chatbot.intrastructure.persistence.repository.component;

import com.openai.chatbot.domain.exception.ChatRepositoryException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pointcut for {@link DataAccessExceptionsHandler}. Indicates that relevant exceptions thrown by the annotated method should be translated into
 * specific subtypes of {@link ChatRepositoryException}. This encompasses handling for standard exceptions related to data integrity, not found
 * scenarios, conflicts, and potentially other custom exception scenarios.
 */
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.RUNTIME )
public @interface HandleDataAccessExceptions{

}
