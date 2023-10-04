package com.openai.chatbot.intrastructure.persistence.db;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_request_message_function_calls" )
public class ChatRequestMessageFunctionCall{

  @Id
  @MapsId
  @OneToOne( fetch = FetchType.LAZY,
             optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "request_id" ),
                          @JoinColumn( name = "message_id",
                                       referencedColumnName = "id" ) },
                foreignKey = @ForeignKey( name = "FK_RMFC_ON_CHAT_REQUEST_MESSAGE" ) )
  private ChatRequestMessage chatRequestMessage;
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( length = 64,
           nullable = false )
  private String name;
  @Column( length = LONG,
           nullable = false )
  private String arguments;

}