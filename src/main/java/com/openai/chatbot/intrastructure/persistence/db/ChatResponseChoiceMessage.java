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
                     "MissingJavadoc" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_response_choice_messages" )
public class ChatResponseChoiceMessage{

  @EqualsAndHashCode.Include
  @Id
  @MapsId
  @OneToOne( optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       nullable = false ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "response_id",
                                       nullable = false ),
                          @JoinColumn( name = "choice_id",
                                       referencedColumnName = "id",
                                       nullable = false ) },
                foreignKey = @ForeignKey( name = "FK_RCM_ON_CHAT_RESPONSE_CHOICE" ) )
  ChatResponseChoice up;
  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  Role role;
  @Column
  String content;
  @OneToOne( mappedBy = ChatResponseChoiceMessageFunctionCall_.UP )
  ChatResponseChoiceMessageFunctionCall chatResponseChoiceMessageFunctionCall;

}