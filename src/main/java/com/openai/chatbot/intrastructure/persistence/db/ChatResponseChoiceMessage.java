package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.common.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_response_choice_messages" )
public class ChatResponseChoiceMessage{

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
  ChatResponseChoice up;
  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  Role role;
  @Column
  String content;
  @OneToOne( mappedBy = ChatResponseChoiceMessageFunctionCall_.UP,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  ChatResponseChoiceMessageFunctionCall functionCall;

  public ChatResponseChoiceMessage functionCall( final ChatResponseChoiceMessageFunctionCall functionCall ){

    this.functionCall = functionCall;
    functionCall.up( this );
    return this;
    
  }

}