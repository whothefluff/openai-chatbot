package com.openai.chatbot.intrastructure.persistence.db.model;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
class JpaChatResponseChoiceMessageTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsParent( ){
    // Arrange
    val choice = new JpaChatResponseChoiceMessageTest.EqualsSpyChatResponseChoice( );
    val message = new JpaChatResponseChoiceMessage( );
    choice.message( message );
    // Act
    message.equals( new JpaChatResponseChoiceMessage( ) );
    // Assert
    assertThat( choice.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsParent( ){
    // Arrange
    val choice = new JpaChatResponseChoiceMessageTest.HashSpyChatResponseChoice( );
    val message = new JpaChatResponseChoiceMessage( );
    choice.message( message );
    // Act
    message.hashCode( );
    // Assert
    assertThat( choice.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( { "com.haulmont.jpb.EqualsDoesntCheckParameterClass",
                       "EqualsAndHashcode",
                       "AutoUnboxing",
                       "NonFinalFieldReferenceInEquals",
                       "ObjectInstantiationInEqualsHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class EqualsSpyChatResponseChoice extends JpaChatResponseChoice{

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
  private static class HashSpyChatResponseChoice extends JpaChatResponseChoice{

    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}