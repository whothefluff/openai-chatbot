package com.openai.chatbot.intrastructure.persistence.db;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
class ChatRequestMessageFunctionCallTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsParent( ){
    // Arrange
    val message = new EqualsSpyChatRequestMessage( );
    val functionCall = new ChatRequestMessageFunctionCall( );
    message.functionCall( functionCall );
    // Act
    functionCall.equals( new ChatRequestMessageFunctionCall( ) );
    // Assert
    assertThat( message.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsParent( ){
    // Arrange
    val message = new HashSpyChatRequestMessage( );
    val functionCall = new ChatRequestMessageFunctionCall( );
    message.functionCall( functionCall );
    // Act
    functionCall.hashCode( );
    // Assert
    assertThat( message.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( { "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode", "AutoUnboxing", "NonFinalFieldReferenceInEquals", "ObjectInstantiationInEqualsHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class EqualsSpyChatRequestMessage extends ChatRequestMessage{

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
  private static class HashSpyChatRequestMessage extends ChatRequestMessage{

    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}