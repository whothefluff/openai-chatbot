package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.domain.exception.ChatRequest_;
import com.openai.chatbot.domain.exception.ChatResponse_;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection", "HardCodedStringLiteral", "ClassWithoutLogger", "MissingJavadoc", "unused" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@ToString( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chats" )
public class Chat{

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
  @Column( insertable = false )
  Instant modifiedAt;
  @Column( unique = true )
  @NaturalId( mutable = true )
  String name;
  @Getter( AccessLevel.PROTECTED )
  @OneToMany( mappedBy = ChatRequest_.CHAT,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<ChatRequest> requests = new LinkedHashSet<>( 0 );
  @Getter( AccessLevel.PROTECTED )
  @OneToMany( mappedBy = ChatResponse_.CHAT,
              cascade = CascadeType.ALL,
              orphanRemoval = true )
  Set<ChatResponse> responses = new LinkedHashSet<>( 0 );

  public Chat addRequest( final ChatRequest request ){

    this.requests( ).add( request );
    request.chat( this );
    return this;

  }

  public Chat removeRequest( final ChatRequest request ){

    this.requests( ).remove( request );
    request.chat( null );
    return this;

  }

  public Chat addResponse( final ChatResponse response ){

    this.responses( ).add( response );
    response.chat( this );
    return this;

  }

  public Chat removeResponse( final ChatResponse response ){

    this.responses( ).remove( response );
    response.chat( null );
    return this;

  }

}