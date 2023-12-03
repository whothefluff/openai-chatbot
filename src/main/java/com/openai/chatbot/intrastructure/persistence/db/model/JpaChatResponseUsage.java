package com.openai.chatbot.intrastructure.persistence.db.model;

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
@Table( name = "chat_response_usages" )
public class JpaChatResponseUsage implements Serializable{

  @Serial
  private static final long serialVersionUID = -8367829910469637657L;
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
  JpaChatResponse response;
  @Column
  Integer promptTokens;
  @Column
  Integer completionTokens;
  @Column
  Integer totalTokens;

}