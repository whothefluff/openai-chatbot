package com.openai.chatbot.intrastructure.persistence.db.model;

import com.openai.chatbot.intrastructure.persistence.db.model.common.Role;
import com.openai.chatbot.intrastructure.persistence.db.model.id.ChatRequestMessageId;
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
                     "MissingJavadoc" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@IdClass( ChatRequestMessageId.class )
@Entity
@Table( name = "chat_request_messages" )
public class JpaChatRequestMessage implements Serializable{

  @Serial
  private static final long serialVersionUID = -5396334612717012318L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "id" ) },
                foreignKey = @ForeignKey( name = "FK_RM_ON_CHAT_REQUEST" ) )
  @Id
  JpaChatRequest request;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue( strategy = GenerationType.SEQUENCE )
  @Id
  Integer id;
  @CreationTimestamp
  @Column( updatable = false,
           nullable = false )
  Instant createdAt;
  @Version
  @UpdateTimestamp
  @Column( insertable = false )
  Instant modifiedAt;
  @Enumerated( EnumType.STRING )
  @Column( nullable = false )
  Role role;
  @Column( length = LONG )
  String content;
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( length = 64 )
  String name;
  @OneToOne( mappedBy = JpaChatRequestMessageFunctionCall_.CHAT_REQUEST_MESSAGE,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  JpaChatRequestMessageFunctionCall functionCall;

  public JpaChatRequestMessage functionCall( final JpaChatRequestMessageFunctionCall functionCall ){

    this.functionCall = functionCall;
    functionCall.chatRequestMessage( this );
    return this;

  }

}