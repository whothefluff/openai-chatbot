package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.id.ChatResponseId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

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
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@IdClass( ChatResponseId.class )
@Entity
@Table( name = "chat_responses" )
public class ChatResponse{

  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( foreignKey = @ForeignKey( name = "FK_RES_ON_CHAT" ) )
  @Id
  Chat chat;
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
  @Column
  String object;
  @Column
  Integer created;
  @Column
  String model;
  @OneToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       insertable = false,
                                       updatable = false ),
                          @JoinColumn( name = "previous_request",
                                       referencedColumnName = "id",
                                       insertable = false,
                                       updatable = false ) },
                foreignKey = @ForeignKey( name = "FK_R_ON_CHAT_REQUEST" ) )
  ChatRequest previousRequest; // not nullable in the database
  @OneToMany( mappedBy = ChatResponseChoice_.RESPONSE,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<ChatResponseChoice> choices = new LinkedHashSet<>( 0 );
  @OneToOne( mappedBy = ChatResponseUsage_.CHAT_RESPONSE,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  ChatResponseUsage usage;

  public ChatResponse addChoice( final ChatResponseChoice choice ){

    this.choices( ).add( choice );
    choice.response( this );
    return this;

  }

  public ChatResponse removeChoice( final ChatResponseChoice choice ){

    this.choices( ).remove( choice );
    choice.response( null );
    return this;

  }

  public ChatResponse usage( final ChatResponseUsage usage ){

    this.usage = usage;
    usage.chatResponse( this );
    return this;

  }

}