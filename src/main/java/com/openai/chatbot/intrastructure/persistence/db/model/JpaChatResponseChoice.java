package com.openai.chatbot.intrastructure.persistence.db.model;

import com.openai.chatbot.intrastructure.persistence.db.model.id.ChatResponseChoiceId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

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
@NoArgsConstructor
@AllArgsConstructor
@Accessors( chain = true,
            fluent = true )
@IdClass( ChatResponseChoiceId.class )
@Entity
@Table( name = "chat_response_choices" )
public class JpaChatResponseChoice implements Serializable{

  @Serial
  private static final long serialVersionUID = -7296913364051407425L;
  @EqualsAndHashCode.Include
  @ToString.Include
  @ManyToOne( fetch = FetchType.LAZY )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id" ),
                          @JoinColumn( name = "response_id",
                                       referencedColumnName = "id" ) },
                foreignKey = @ForeignKey( name = "FK_RC_ON_CHAT_RESPONSE" ) )
  @Id
  JpaChatResponse response;
  @EqualsAndHashCode.Include
  @ToString.Include
  @GeneratedValue( strategy = GenerationType.SEQUENCE )
  @SequenceGenerator( name = "chat_response_choices_id_gen",
                      allocationSize = 1 )
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
  Integer index;
  @Column
  String finishReason;
  @OneToOne( mappedBy = JpaChatResponseChoiceMessage_.CHOICE,
             cascade = CascadeType.ALL,
             orphanRemoval = true )
  JpaChatResponseChoiceMessage message;

  public JpaChatResponseChoice message( final JpaChatResponseChoiceMessage message ){

    this.message = message;
    message.choice( this );
    return this;

  }

}