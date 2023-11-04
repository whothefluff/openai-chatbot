package com.openai.chatbot.intrastructure.persistence.db.id;

import com.openai.chatbot.intrastructure.persistence.db.JpaChatRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger" } )
@Data
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
public class ChatRequestFunctionDefinitionId implements Serializable{

  @Serial
  private static final long serialVersionUID = -5499262376651780785L;
  JpaChatRequest request;
  Integer id;

}