package com.openai.chatbot.intrastructure.persistence.db;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_response_choice_message_function_calls" )
public class ChatResponseChoiceMessageFunctionCall{

  @EqualsAndHashCode.Include
  @Id
  @MapsId
  @OneToOne( targetEntity = ChatResponseChoiceMessage.class,
             optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       nullable = false ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "response_id",
                                       nullable = false ),
                          @JoinColumn( name = "choice_id",
                                       referencedColumnName = "choice_id",
                                       nullable = false ) },
                foreignKey = @ForeignKey( name = "FK_RCMFC_ON_RESPONSE_CHOICE_MESSAGE" ) )
  ChatResponseChoiceMessage up;
  @Column( nullable = false )
  String name;
  @Column( nullable = false )
  String arguments;

}