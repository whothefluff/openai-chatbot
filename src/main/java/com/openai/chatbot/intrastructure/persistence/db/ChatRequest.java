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
public class ChatRequest{

  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( foreignKey = @ForeignKey( name = "FK_REQ_ON_CHAT" ) )
  @Id
  Chat chat;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue( strategy = GenerationType.TABLE )
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
  ChatResponse previousResponse;
  @OneToMany( mappedBy = ChatRequestFunctionDefinition_.REQUEST,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<ChatRequestFunctionDefinition> chatRequestFunctionDefinitions = new LinkedHashSet<>( 0 );
  @OneToMany( mappedBy = ChatRequestMessage_.CHAT_REQUEST )
  Set<ChatRequestMessage> chatRequestMessages = new LinkedHashSet<>( 0 );

  public ChatRequest addFunctionDefinition( final ChatRequestFunctionDefinition funcDefinition ){

    this.chatRequestFunctionDefinitions( ).add( funcDefinition );
    funcDefinition.request( this );
    return this;

  }

  public ChatRequest removeFunctionDefinition( final ChatRequestFunctionDefinition funcDefinition ){

    this.chatRequestFunctionDefinitions( ).remove( funcDefinition );
    funcDefinition.request( null );
    return this;

  }

  public ChatRequest addMessage( final ChatRequestMessage message ){

    this.chatRequestMessages( ).add( message );
    message.chatRequest( this );
    return this;

  }

  public ChatRequest removeMessage( final ChatRequestMessage message ){

    this.chatRequestMessages( ).remove( message );
    message.chatRequest( null );
    return this;

  }

}
