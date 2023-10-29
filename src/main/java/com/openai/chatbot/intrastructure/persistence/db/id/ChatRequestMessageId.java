package com.openai.chatbot.intrastructure.persistence.db.id;

import com.openai.chatbot.intrastructure.persistence.db.ChatRequest;
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
public class ChatRequestMessageId implements Serializable{

  @Serial
  private static final long serialVersionUID = -5662201856499417990L;
  ChatRequest request;
  Integer id;

}