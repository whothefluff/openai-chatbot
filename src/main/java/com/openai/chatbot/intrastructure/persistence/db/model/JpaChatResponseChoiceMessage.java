package com.openai.chatbot.intrastructure.persistence.db.model;

import com.openai.chatbot.intrastructure.persistence.db.model.common.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc",
                     "com.haulmont.jpb.LombokToStringIncludeInspection" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@ToString( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_response_choice_messages" )
public class JpaChatResponseChoiceMessage implements Serializable{

  @Serial
  private static final long serialVersionUID = 3189574518846497054L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @Id
  @MapsId
  @OneToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "response_id" ),
                          @JoinColumn( name = "choice_id",
                                       referencedColumnName = "id" ) },
                foreignKey = @ForeignKey( name = "FK_RCM_ON_CHAT_RESPONSE_CHOICE" ) )
  JpaChatResponseChoice choice;
  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  Role role;
  @Column
  String content;
  @OneToOne( mappedBy = JpaChatResponseChoiceMessageFunctionCall_.MESSAGE,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  JpaChatResponseChoiceMessageFunctionCall functionCall;

  public JpaChatResponseChoiceMessage functionCall( final JpaChatResponseChoiceMessageFunctionCall functionCall ){

    this.functionCall = functionCall;
    functionCall.message( this );
    return this;

  }

}