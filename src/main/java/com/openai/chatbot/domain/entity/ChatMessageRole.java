package com.openai.chatbot.domain.entity;

/**
 * The role of the messages author. One of system, user, assistant, or function
 */
@SuppressWarnings( { "MissingJavadoc", "HardCodedStringLiteral", "InnerClassOfInterface" } )
public enum ChatMessageRole{
  system, user, assistant, function

}