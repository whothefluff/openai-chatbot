package com.openai.chatbot.intrastructure.persistence.db.model;

import com.openai.chatbot.intrastructure.persistence.db.model.id.ChatRequestFunctionDefinitionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

import static org.hibernate.Length.LONG;

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
@IdClass( ChatRequestFunctionDefinitionId.class )
@Entity
@Table( name = "chat_request_function_definitions" )
public class JpaChatRequestFunctionDefinition implements Serializable{

  @Serial
  private static final long serialVersionUID = -8678545769169230397L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       nullable = false ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "id",
                                       nullable = false ) },
                foreignKey = @ForeignKey( name = "FK_RFD_ON_CHAT_REQUEST" ) )
  @Id
  JpaChatRequest request;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue( strategy = GenerationType.SEQUENCE )
  @SequenceGenerator( name = "chat_request_function_definitions_id_gen",
                      allocationSize = 1 )
  @Id
  Integer id;
  @CreationTimestamp
  @Column( updatable = false,
           nullable = false )
  Instant createdAt;
  @Version
  @UpdateTimestamp
  @Column( nullable = false )
  Instant modifiedAt;
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( length = 64,
           nullable = false )
  String name;
  @Column
  String description;
  @Column( nullable = false,
           length = LONG )
  String parameters;

}