package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponse;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatResponseUsage;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
class JpaChatResponseUsageTest{

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void equals_instance_callsParent( ){
    // Arrange
    val response = new EqualsSpyChatResponse( );
    val usage = new JpaChatResponseUsage( );
    response.usage( usage );
    // Act
    usage.equals( new JpaChatResponseUsage( ) );
    // Assert
    assertThat( response.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( "ResultOfMethodCallIgnored" )
  @Test
  public void hashCode_instance_callsParent( ){
    // Arrange
    val response = new HashSpyChatResponse( );
    val usage = new JpaChatResponseUsage( );
    response.usage( usage );
    // Act
    usage.hashCode( );
    // Assert
    assertThat( response.calls( ) ).isEqualTo( 1 );

  }

  @SuppressWarnings( { "com.haulmont.jpb.EqualsDoesntCheckParameterClass",
                       "EqualsAndHashcode",
                       "AutoUnboxing",
                       "NonFinalFieldReferenceInEquals",
                       "ObjectInstantiationInEqualsHashCode" } )
  @Getter
  @Accessors( chain = true,
              fluent = true )
  private static class EqualsSpyChatResponse extends JpaChatResponse{

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
  private static class HashSpyChatResponse extends JpaChatResponse{

    Integer calls = 0;

    @Override
    public int hashCode( ){

      this.calls++;
      return 1;

    }

  }

}