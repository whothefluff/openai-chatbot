package com.openai.chatbot.intrastructure.persistence.db.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

import static org.hibernate.Length.LONG;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_request_message_function_calls" )
public class JpaChatRequestMessageFunctionCall implements Serializable{

  @Serial
  private static final long serialVersionUID = 8094349740683906916L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @Id
  @MapsId
  @OneToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "request_id" ),
                          @JoinColumn( name = "message_id",
                                       referencedColumnName = "id" ) },
                foreignKey = @ForeignKey( name = "FK_RMFC_ON_CHAT_REQUEST_MESSAGE" ) )
  JpaChatRequestMessage chatRequestMessage; //JPA Buddy fails to preview the DDL, but it is generated correctly
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( length = 64,
           nullable = false )
  String name;
  @Column( length = LONG,
           nullable = false )
  String arguments;

}