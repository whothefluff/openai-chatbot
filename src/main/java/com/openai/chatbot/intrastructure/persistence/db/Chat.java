package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.domain.exception.ChatRequest_;
import com.openai.chatbot.domain.exception.ChatResponse_;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings( { "com.haulmont.jpb.LombokDataInspection", "com.haulmont.jpb.LombokEqualsAndHashCodeInspection", "HardCodedStringLiteral", "ClassWithoutLogger", "MissingJavadoc" } )
@Data
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
@FieldDefaults( level = AccessLevel.PROTECTED )
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@Entity
@Table( name = "chats" )
public class Chat{

  @EqualsAndHashCode.Include
  @GeneratedValue
  @UuidGenerator
  @Id
  @Column
  UUID id;
  @CreationTimestamp
  @Column( updatable = false )
  Instant createdAt;
  @Version
  @UpdateTimestamp
  @Column( insertable = false )
  Instant modifiedAt;
  @Column( unique = true )
  String name;
  @OneToMany( mappedBy = ChatRequest_.CHAT )
  Set<ChatRequest> requests = new LinkedHashSet<>( 0 );
  @OneToMany( mappedBy = ChatResponse_.CHAT )
  Set<ChatResponse> responses = new LinkedHashSet<>( 0 );

}