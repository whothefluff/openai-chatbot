package com.openai.chatbot.intrastructure.persistence.db;

import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequest;
import com.openai.chatbot.intrastructure.persistence.db.model.JpaChatRequestMessage;
import com.openai.chatbot.intrastructure.persistence.db.model.common.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral", "AutoBoxing" } )
@ActiveProfiles( "test" )
@DataJpaTest
class JpaChatRequestMessageTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameRequestAndId_equal( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestMessageTest.FakeEqualChatRequest( );
    val message1 = new JpaChatRequestMessage( ).request( request ).id( id );
    val message2 = new JpaChatRequestMessage( ).request( request ).id( id );
    // Act & Assert
    assertThat( message1 ).isEqualTo( message2 );

  }

  @Test
  public void equals_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new JpaChatRequestMessageTest.FakeDifferentChatRequest( );
    val message1 = new JpaChatRequestMessage( ).request( request1 ).id( id );
    val request2 = new JpaChatRequest( );
    val message2 = new JpaChatRequestMessage( ).request( request2 ).id( id );
    // Act & Assert
    assertThat( message1 ).isNotEqualTo( message2 );

  }

  @Test
  public void equals_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestMessageTest.FakeEqualChatRequest( );
    val message1 = new JpaChatRequestMessage( ).request( request ).id( id );
    val message2 = new JpaChatRequestMessage( ).request( request ).id( id + 1 );
    // Act & Assert
    assertThat( message1 ).isNotEqualTo( message2 );

  }

  @Test
  public void hashCode_sameRequestAndId_equal( ){
    // Arrange
    val id = Integer.valueOf( new SecureRandom( ).nextInt( ) );
    val request = new JpaChatRequestMessageTest.FakeChatRequest( );
    val message1 = new JpaChatRequestMessage( ).request( request ).id( id );
    val message2 = new JpaChatRequestMessage( ).request( request ).id( id );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertThat( message1Hash ).isEqualTo( message2Hash );

  }

  @Test
  public void hashCode_differentRequestAndSameId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request1 = new JpaChatRequestMessageTest.FakeChatRequest( 1 );
    val message1 = new JpaChatRequestMessage( ).request( request1 ).id( id );
    val request2 = new JpaChatRequestMessageTest.FakeChatRequest( 2 );
    val message2 = new JpaChatRequestMessage( ).request( request2 ).id( id );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertThat( message1Hash ).isNotEqualTo( message2Hash );

  }

  @Test
  public void hashCode_sameRequestAndDifferentId_notEqual( ){
    // Arrange
    val id = new SecureRandom( ).nextInt( );
    val request = new JpaChatRequestMessageTest.FakeChatRequest( );
    val message1 = new JpaChatRequestMessage( ).request( request ).id( id );
    val message2 = new JpaChatRequestMessage( ).request( request ).id( id + 1 );
    // Act
    val message1Hash = message1.hashCode( );
    val message2Hash = message2.hashCode( );
    // Assert
    assertThat( message1Hash ).isNotEqualTo( message2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new JpaChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( request.id( ) ).isPositive( );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new JpaChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    // Act
    this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( message.createdAt( ), Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new JpaChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( message );
    val initialValue = message.createdAt( );
    // Act
    message.role( Role.system );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = message.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    val request = new JpaChatRequest( ).model( "some model" );
    chat.addRequest( request );
    val message = new JpaChatRequestMessage( ).role( Role.user );
    request.addMessage( message );
    this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( message );
    // Act
    message.role( Role.system );
    this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = message.createdAt( );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @SuppressWarnings( { "NonFinalFieldReferencedInHashCode", "EqualsAndHashcode" } )
  @NoArgsConstructor
  @AllArgsConstructor
  private static class FakeChatRequest extends JpaChatRequest{

    protected int hashCode;

    @Override
    public int hashCode( ){

      return this.hashCode;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "com.haulmont.jpb.EqualsDoesntCheckParameterClass", "EqualsAndHashcode" } )
  private static class FakeEqualChatRequest extends FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return true;

    }

  }

  @SuppressWarnings( { "ClassTooDeepInInheritanceTree", "EqualsAndHashcode" } )
  private static class FakeDifferentChatRequest extends FakeChatRequest{

    @Override
    public boolean equals( final Object o ){

      return false;

    }

  }

}