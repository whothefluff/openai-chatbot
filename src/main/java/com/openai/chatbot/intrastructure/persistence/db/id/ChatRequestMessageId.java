package com.openai.chatbot.intrastructure.persistence.db.id;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger" } )
@Data
@FieldDefaults( level = AccessLevel.PROTECTED )
@Accessors( chain = true,
            fluent = true )
@Embeddable
public class ChatRequestMessageId implements Serializable{

  @Serial
  private static final long serialVersionUID = -5662201856499417990L;
  UUID chatId;
  Integer requestId;
  Integer id;

}