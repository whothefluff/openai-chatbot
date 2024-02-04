package com.openai.chatbot.intrastructure.persistence.db.model;

import com.openai.chatbot.intrastructure.persistence.db.model.id.ChatResponseId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "UseOfConcreteClass",
                     "ClassWithoutLogger",
                     "MissingJavadoc",
                     "com.haulmont.jpb.LombokToStringIncludeInspection",
                     "unused" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@ToString( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@IdClass( ChatResponseId.class )
@Entity
@Table( name = "chat_responses" )
public class JpaChatResponse implements Serializable{

  @Serial
  private static final long serialVersionUID = 1726339493869681304L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumn( foreignKey = @ForeignKey( name = "FK_RES_ON_CHAT" ) )
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
  @Column( nullable = false )
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
  JpaChatRequest previousRequest; // not nullable in the database
  @OneToMany( mappedBy = JpaChatResponseChoice_.RESPONSE,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<JpaChatResponseChoice> choices = new LinkedHashSet<>( 0 );
  @OneToOne( mappedBy = JpaChatResponseUsage_.RESPONSE,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  JpaChatResponseUsage usage;

  public JpaChatResponse addChoice( final JpaChatResponseChoice choice ){

    this.choices( ).add( choice );
    choice.response( this );
    return this;

  }

  public JpaChatResponse removeChoice( final JpaChatResponseChoice choice ){

    this.choices( ).remove( choice );
    choice.response( null );
    return this;

  }

  public JpaChatResponse usage( final JpaChatResponseUsage usage ){

    this.usage = usage;
    usage.response( this );
    return this;

  }

}