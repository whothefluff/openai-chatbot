package com.openai.chatbot.intrastructure.persistence.db;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class ChatResponseChoiceMessageTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsMessage( ){
    // Arrange
    val choice = new ChatResponseChoiceMessageTest.EqualsSpyChatResponseChoice( );
    val message = new ChatResponseChoiceMessage( );
    choice.message( message );
    // Act
    message.equals( new ChatResponseChoiceMessage( ) );
    // Assert
    assertThat( choice.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsMessage( ){
    // Arrange
    val choice = new ChatResponseChoiceMessageTest.HashSpyChatResponseChoice( );
    val message = new ChatResponseChoiceMessage( );
    choice.message( message );
    // Act
    message.hashCode( );
    // Assert
    assertThat( choice.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( { "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode", "AutoUnboxing", "NonFinalFieldReferenceInEquals", "ObjectInstantiationInEqualsHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class EqualsSpyChatResponseChoice extends ChatResponseChoice{

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
  private static class HashSpyChatResponseChoice extends ChatResponseChoice{

    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}