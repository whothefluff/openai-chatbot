package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.common.Role;
import com.openai.chatbot.intrastructure.persistence.db.id.ChatRequestMessageId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import static org.hibernate.Length.LONG;

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
@Entity
@Table( name = "chat_request_messages" )
public class ChatRequestMessage{

  @EqualsAndHashCode.Include
  @EmbeddedId
  @AttributeOverrides( { @AttributeOverride( name = "chatId",
                                             column = @Column( name = "chat_id" ) ),
                         @AttributeOverride( name = "requestId",
                                             column = @Column( name = "request_id" ) ) } )
  ChatRequestMessageId key;
  @ManyToOne( fetch = FetchType.LAZY,
              optional = false )
  @MapsId( "chat_id" )
  @JoinColumns( value = { @JoinColumn( name = "chat_id",
                                       referencedColumnName = "chat_id",
                                       insertable = false,
                                       updatable = false ),
                          @JoinColumn( name = "request_id",
                                       referencedColumnName = "id",
                                       insertable = false,
                                       updatable = false ) },
                foreignKey = @ForeignKey( name = "FK_RM_ON_CHAT_REQUEST" ) )
  ChatRequest chatRequest;
  @Enumerated( EnumType.STRING )
  @Column( name = "role",
           nullable = false )
  Role role;
  @Column( name = "content",
           length = LONG )
  String content;
  @Pattern( regexp = "^[a-zA-Z0-9_]{1,64}$",
            message = "Invalid function name. Only a-z, A-Z, 0-9, and underscores are allowed, with a maximum length of 64 characters." )
  @Column( name = "name",
           length = 64 )
  String name;
  @OneToOne( cascade = CascadeType.ALL,
             orphanRemoval = true,
             mappedBy = ChatRequestMessageFunctionCall_.CHAT_REQUEST_MESSAGE )
  ChatRequestMessageFunctionCall functionCall;

}