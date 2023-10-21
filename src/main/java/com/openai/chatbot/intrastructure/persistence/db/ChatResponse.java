package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.domain.exception.ChatResponseChoice_;
import com.openai.chatbot.domain.exception.ChatResponseUsage_;
import com.openai.chatbot.intrastructure.persistence.db.id.ChatResponseId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Set;

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
@IdClass( ChatResponseId.class )
@Entity
@Table( name = "chat_responses" )
public class ChatResponse{

  @EqualsAndHashCode.Include
  @ManyToOne( fetch = FetchType.LAZY,
              optional = false )
  @JoinColumn( foreignKey = @ForeignKey( name = "FK_RES_ON_CHAT" ) )
  @Id
  Chat chat;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  @Id
  Integer id;
  @Column
  String object;
  @Column
  Integer created;
  @Column
  String model;
  @OneToOne( fetch = FetchType.LAZY,
             optional = false )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       insertable = false,
                                       updatable = false ),
                          @JoinColumn( name = "previous_request",
                                       referencedColumnName = "id",
                                       insertable = false,
                                       updatable = false ) },
                foreignKey = @ForeignKey( name = "FK_R_ON_CHAT_REQUEST" ) )
  ChatRequest previousRequest;
  @OneToMany( mappedBy = ChatResponseChoice_.CHAT_RESPONSE )
  Set<ChatResponseChoice> chatResponseChoices = new LinkedHashSet<>( 0 );
  @OneToOne( mappedBy = ChatResponseUsage_.CHAT_RESPONSE )
  ChatResponseUsage chatResponseUsage;

}