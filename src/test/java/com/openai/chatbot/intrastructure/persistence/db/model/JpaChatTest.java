package com.openai.chatbot.intrastructure.persistence.db.model;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings( { "ClassWithoutLogger", "HardCodedStringLiteral" } )
@ActiveProfiles( "test" )
@DataJpaTest
class JpaChatTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameId_equal( ){
    // Arrange
    val uuid = UUID.randomUUID( );
    val chat1 = new JpaChat( );
    chat1.id( uuid );
    val chat2 = new JpaChat( );
    chat2.id( uuid );
    // Act & Assert
    assertThat( chat1 ).isEqualTo( chat2 );

  }

  @Test
  public void equals_differentId_notEqual( ){
    // Arrange
    val chat1 = new JpaChat( );
    chat1.id( UUID.randomUUID( ) );
    val chat2 = new JpaChat( );
    chat2.id( UUID.randomUUID( ) );
    // Act & Assert
    assertThat( chat1 ).isNotEqualTo( chat2 );

  }

  @Test
  public void hashCode_sameId_equal( ){
    // Arrange
    val uuid = UUID.randomUUID( );
    val chat1 = new JpaChat( );
    chat1.id( uuid );
    val chat2 = new JpaChat( );
    chat2.id( uuid );
    // Act
    val chat1Hash = chat1.hashCode( );
    val chat2Hash = chat2.hashCode( );
    // Assert
    assertThat( chat1Hash ).isEqualTo( chat2Hash );

  }

  @Test
  public void hashCode_differentId_notEqual( ){
    // Arrange
    val chat1 = new JpaChat( );
    chat1.id( UUID.randomUUID( ) );
    val chat2 = new JpaChat( );
    chat2.id( UUID.randomUUID( ) );
    // Act
    val chat1Hash = chat1.hashCode( );
    val chat2Hash = chat2.hashCode( );
    // Assert
    assertThat( chat1Hash ).isNotEqualTo( chat2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new JpaChat( );
    // Act
    val savedChat = this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( savedChat.id( ) ).isNotNull( );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( );
    // Act
    val savedChat = this.entityManager.persistAndFlush( chat );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( chat.createdAt( ), Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new JpaChat( ).name( "initial" );
    val savedChat = this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( savedChat );
    val initialValue = savedChat.createdAt( );
    // Act
    chat.name( "updated" );
    val updatedchat = this.entityManager.persistAndFlush( chat );
    val valueAfterUpdate = updatedchat.createdAt( );
    // Assert
    assertThat( initialValue ).isEqualTo( valueAfterUpdate );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new JpaChat( ).name( "initial" );
    val savedChat = this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( savedChat );
    // Act
    savedChat.name( "updated" );
    val updatedchat = this.entityManager.persistAndFlush( savedChat );
    val valueAfterUpdate = updatedchat.createdAt( );
    // Assert
    assertThat( ChronoUnit.SECONDS.between( valueAfterUpdate, Instant.now( ) ) ).isLessThan( maxAllowedDifference );

  }

}
