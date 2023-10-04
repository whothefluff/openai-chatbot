package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.domain.exception.ChatRequestFunctionDefinition_;
import com.openai.chatbot.domain.exception.ChatRequestMessage_;
import com.openai.chatbot.intrastructure.persistence.db.id.ChatRequestId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc",
                     "ClassWithTooManyFields" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chat_requests" )
public class ChatRequest{

  @EqualsAndHashCode.Include
  @EmbeddedId
  @AttributeOverride( name = "chatId",
                      column = @Column( name = "chat_id" ) )
  ChatRequestId key;
  @MapsId
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( name = "chat_id",
               referencedColumnName = "id",
               nullable = false,
               foreignKey = @ForeignKey( name = "FK_REQ_ON_CHAT" ) )
  Chat chat;
  @Column
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  Integer id;
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
  @Column
  String user;
  @OneToOne( fetch = FetchType.LAZY,
             optional = false )
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
  @OneToMany( mappedBy = ChatRequestFunctionDefinition_.CHAT_REQUEST )
  Set<ChatRequestFunctionDefinition> chatRequestFunctionDefinitions = new LinkedHashSet<>( 0 );
  @OneToMany( mappedBy = ChatRequestMessage_.CHAT_REQUEST )
  Set<ChatRequestMessage> chatRequestMessages = new LinkedHashSet<>( 0 );

}
