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
@Table( name = "chat_response_usages" )
public class ChatResponseUsage{

  @EqualsAndHashCode.Include
  @ToString.Include
  @Id
  @MapsId
  @OneToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "id" ), },
                foreignKey = @ForeignKey( name = "FK_RU_ON_CHAT_RESPONSE" ) )
  ChatResponse chatResponse;
  @Column
  Integer promptTokens;
  @Column
  Integer completionTokens;
  @Column
  Integer totalTokens;

}