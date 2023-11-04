package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.id.ChatRequestId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc",
                     "ClassWithTooManyFields",
                     "com.haulmont.jpb.LombokToStringIncludeInspection" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@ToString( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@IdClass( ChatRequestId.class )
@Entity
@Table( name = "chat_requests" )
public class JpaChatRequest{

  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( foreignKey = @ForeignKey( name = "FK_REQ_ON_CHAT" ) )
  @Id
  JpaChat chat;
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
  @Column( nullable = false )
  String model;
  @Column
  String functionCall;
  @Column( precision = 10 )
  BigDecimal temperature;
  @Column( precision = 10 )
  BigDecimal topP;
  @Column
  Integer n;
  @Column
  Boolean stream;
  @Column
  Integer maxTokens;
  @Column( precision = 10 )
  BigDecimal presencePenalty;
  @Column( precision = 10 )
  BigDecimal frequencyPenalty;
  @Column
  String logitBias;
  @Column( name = "\"user\"" )
  String user;
  @OneToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       insertable = false,
                                       updatable = false ),
                          @JoinColumn( name = "previous_response",
                                       referencedColumnName = "id",
                                       insertable = false,
                                       updatable = false ) },
                foreignKey = @ForeignKey( name = "FK_R_ON_CHAT_RESPONSE" ) )
  JpaChatResponse previousResponse;
  @OneToMany( mappedBy = JpaChatRequestFunctionDefinition_.REQUEST,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<JpaChatRequestFunctionDefinition> functionDefinitions = new LinkedHashSet<>( 0 );
  @OneToMany( mappedBy = JpaChatRequestMessage_.REQUEST,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<JpaChatRequestMessage> messages = new LinkedHashSet<>( 0 );

  public JpaChatRequest addFunctionDefinition( final JpaChatRequestFunctionDefinition funcDefinition ){

    this.functionDefinitions( ).add( funcDefinition );
    funcDefinition.request( this );
    return this;

  }

  public JpaChatRequest removeFunctionDefinition( final JpaChatRequestFunctionDefinition funcDefinition ){

    this.functionDefinitions( ).remove( funcDefinition );
    funcDefinition.request( null );
    return this;

  }

  public JpaChatRequest addMessage( final JpaChatRequestMessage message ){

    this.messages( ).add( message );
    message.request( this );
    return this;

  }

  public JpaChatRequest removeMessage( final JpaChatRequestMessage message ){

    this.messages( ).remove( message );
    message.request( null );
    return this;

  }

}
