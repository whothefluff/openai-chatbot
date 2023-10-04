package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.id.ChatRequestFunctionDefinitionId;
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
@Table( name = "chat_request_function_definitions" )
public class ChatRequestFunctionDefinition{

  @EqualsAndHashCode.Include
  @EmbeddedId
  @AttributeOverrides( { @AttributeOverride( name = "chatId",
                                             column = @Column( name = "chat_id" ) ),
                         @AttributeOverride( name = "requestId",
                                             column = @Column( name = "request_id" ) ) } )
  private ChatRequestFunctionDefinitionId key;
  @MapsId( "chat_id" )
  @ManyToOne( fetch = FetchType.LAZY,
              optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       nullable = false ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "id",
                                       nullable = false ) },
                foreignKey = @ForeignKey( name = "FK_RFD_ON_CHAT_REQUEST" ) )
  private ChatRequest chatRequest;
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( length = 64,
           nullable = false )
  private String name;
  @Column( name = "description" )
  private String description;
  @Column( nullable = false,
           length = LONG )
  private String parameters;

}