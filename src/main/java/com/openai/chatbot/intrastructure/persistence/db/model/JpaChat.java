package com.openai.chatbot.intrastructure.persistence.db.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection",
                     "com.haulmont.jpb.LombokEqualsAndHashCodeInspection",
                     "HardCodedStringLiteral",
                     "ClassWithoutLogger",
                     "MissingJavadoc",
                     "unused" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@ToString( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chats" )
public class JpaChat implements Serializable{

  @Serial
  private static final long serialVersionUID = -7278882620091527315L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue
  @UuidGenerator
  @Id
  @Column
  UUID id;
  @CreationTimestamp
  @Column( updatable = false,
           nullable = false )
  Instant createdAt;
  @Version
  @UpdateTimestamp
  @Column( nullable = false )
  Instant modifiedAt;
  @Column( unique = true )
  @NaturalId( mutable = true )
  String name;
  @Getter( AccessLevel.PROTECTED )
  @OneToMany( mappedBy = JpaChatRequest_.CHAT,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<JpaChatRequest> requests = new LinkedHashSet<>( 0 );
  @Getter( AccessLevel.PROTECTED )
  @OneToMany( mappedBy = JpaChatResponse_.CHAT,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<JpaChatResponse> responses = new LinkedHashSet<>( 0 );

  public JpaChat addRequest( final JpaChatRequest request ){

    this.requests( ).add( request );
    request.chat( this );
    return this;

  }

  public JpaChat removeRequest( final JpaChatRequest request ){

    this.requests( ).remove( request );
    request.chat( null );
    return this;

  }

  public JpaChat addResponse( final JpaChatResponse response ){

    this.responses( ).add( response );
    response.chat( this );
    return this;

  }

  public JpaChat removeResponse( final JpaChatResponse response ){

    this.responses( ).remove( response );
    response.chat( null );
    return this;

  }

}