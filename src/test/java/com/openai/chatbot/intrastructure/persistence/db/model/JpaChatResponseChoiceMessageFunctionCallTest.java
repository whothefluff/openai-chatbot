package com.openai.chatbot.intrastructure.persistence.db.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
class JpaChatResponseChoiceMessageFunctionCallTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsParent( ){
    // Arrange
    val message = new JpaChatResponseChoiceMessageFunctionCallTest.EqualsSpyChatResponseChoiceMessage( );
    val functionCall = new JpaChatResponseChoiceMessageFunctionCall( );
    message.functionCall( functionCall );
    // Act
    functionCall.equals( new JpaChatResponseChoiceMessageFunctionCall( ) );
    // Assert
    assertThat( message.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsParent( ){
    // Arrange
    val message = new JpaChatResponseChoiceMessageFunctionCallTest.HashSpyChatResponseChoiceMessage( );
    val functionCall = new JpaChatResponseChoiceMessageFunctionCall( );
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
  private static class EqualsSpyChatResponseChoiceMessage extends JpaChatResponseChoiceMessage{

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
  private static class HashSpyChatResponseChoiceMessage extends JpaChatResponseChoiceMessage{

    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}