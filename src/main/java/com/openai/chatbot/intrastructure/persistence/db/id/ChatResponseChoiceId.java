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
public class ChatResponseChoiceId implements Serializable{

  @Serial
  private static final long serialVersionUID = 3397766728410424276L;
  private UUID chatId;
  private Integer responseId;
  private Integer id;

}