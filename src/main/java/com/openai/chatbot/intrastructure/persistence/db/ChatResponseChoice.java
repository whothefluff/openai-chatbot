package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.id.ChatResponseChoiceId;
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
@Table( name = "chat_response_choices" )
public class ChatResponseChoice{

  @EqualsAndHashCode.Include
  @EmbeddedId
  @AttributeOverrides( { @AttributeOverride( name = "chatId",
                                             column = @Column( name = "chat_id" ) ),
                         @AttributeOverride( name = "responseId",
                                             column = @Column( name = "response_id" ) ) } )
  private ChatResponseChoiceId key;
  @MapsId( "chat_id" )
  @ManyToOne( fetch = FetchType.LAZY,
              optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       nullable = false ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "id",
                                       nullable = false ) },
                foreignKey = @ForeignKey( name = "FK_RC_ON_CHAT_RESPONSE" ) )
  private ChatResponse chatResponse;
  @Column
  private Integer index;
  @Column
  private String finishReason;
  @OneToOne( mappedBy = ChatResponseChoiceMessage_.UP )
  private ChatResponseChoiceMessage chatResponseChoiceMessage;

}