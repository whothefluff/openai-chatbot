package com.openai.chatbot.intrastructure.persistence.db.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.Serial;

import static org.assertj.core.api.Assertions.assertThat;

class JpaChatRequestMessageFunctionCallTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsParent( ){
    // Arrange
    val message = new EqualsSpyChatRequestMessage( );
    val functionCall = new JpaChatRequestMessageFunctionCall( );
    message.functionCall( functionCall );
    // Act
    functionCall.equals( new JpaChatRequestMessageFunctionCall( ) );
    // Assert
    assertThat( message.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsParent( ){
    // Arrange
    val message = new HashSpyChatRequestMessage( );
    val functionCall = new JpaChatRequestMessageFunctionCall( );
    message.functionCall( functionCall );
    // Act
    functionCall.hashCode( );
    // Assert
    assertThat( message.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( { "com.haulmont.jpb.EqualsDoesntCheckParameterClass",
                       "EqualsAndHashcode",
                       "AutoUnboxing",
                       "NonFinalFieldReferenceInEquals",
                       "ObjectInstantiationInEqualsHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class EqualsSpyChatRequestMessage extends JpaChatRequestMessage{

    @Serial
    private static final long serialVersionUID = 8296074535186537575L;
    Integer calls = 0;

    @Override
    public boolean equals( final Object o ){

      this.calls++;
      return true;

    }

  }

  @SuppressWarnings( { "EqualsAndHashcode", "AutoUnboxing", "ObjectInstantiationInEqualsHashCode", "NonFinalFieldReferencedInHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class HashSpyChatRequestMessage extends JpaChatRequestMessage{

    @Serial
    private static final long serialVersionUID = 3520723138234176845L;
    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}