package com.openai.chatbot.intrastructure.persistence.db;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings( { "MissingJavadoc", "ClassWithoutLogger", "HardCodedStringLiteral" } )
@ActiveProfiles( "test" )
@DataJpaTest
public class ChatTest{

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void equals_sameId_equal( ){
    // Arrange
    val uuid = UUID.randomUUID( );
    val chat1 = new Chat( );
    chat1.id( uuid );
    val chat2 = new Chat( );
    chat2.id( uuid );
    // Act & Assert
    assertEquals( chat1, chat2 );

  }

  @Test
  public void equals_differentId_notEqual( ){
    // Arrange
    val chat1 = new Chat( );
    chat1.id( UUID.randomUUID( ) );
    val chat2 = new Chat( );
    chat2.id( UUID.randomUUID( ) );
    // Act & Assert
    assertNotEquals( chat1, chat2 );

  }

  @Test
  public void hashCode_sameId_equal( ){
    // Arrange
    val uuid = UUID.randomUUID( );
    val chat1 = new Chat( );
    chat1.id( uuid );
    val chat2 = new Chat( );
    chat2.id( uuid );
    // Act
    val chat1Hash = chat1.hashCode( );
    val chat2Hash = chat2.hashCode( );
    // Assert
    assertEquals( chat1Hash, chat2Hash );

  }

  @Test
  public void hashCode_differentId_notEqual( ){
    // Arrange
    val chat1 = new Chat( );
    chat1.id( UUID.randomUUID( ) );
    val chat2 = new Chat( );
    chat2.id( UUID.randomUUID( ) );
    // Act
    val chat1Hash = chat1.hashCode( );
    val chat2Hash = chat2.hashCode( );
    // Assert
    assertNotEquals( chat1Hash, chat2Hash );

  }

  @Test
  public void id_afterSave_notNull( ){
    // Arrange
    val chat = new Chat( );
    // Act
    val savedChat = this.entityManager.persistAndFlush( chat );
    // Assert
    assertNotNull( savedChat.id( ) );

  }

  @Test
  public void createdAt_afterCreation_recent( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( );
    // Act
    val savedChat = this.entityManager.persistAndFlush( chat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( savedChat.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

  @Test
  public void createdAt_afterModification_unchanged( ){
    // Arrange
    val chat = new Chat( ).name( "initial" );
    val savedChat = this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( savedChat );
    val initialValue = savedChat.createdAt( );
    // Act
    chat.name( "updated" );
    val updatedchat = this.entityManager.persistAndFlush( chat );
    // Assert
    assertEquals( initialValue, updatedchat.createdAt( ) );

  }

  @Test
  public void modifiedAt_afterModification_updated( ){
    // Arrange
    val maxAllowedDifference = 30L;
    val chat = new Chat( ).name( "initial" );
    val savedChat = this.entityManager.persistAndFlush( chat );
    this.entityManager.refresh( savedChat );
    // Act
    savedChat.name( "updated" );
    val updatedchat = this.entityManager.persistAndFlush( savedChat );
    // Assert
    assertTrue( ChronoUnit.SECONDS.between( updatedchat.createdAt( ), Instant.now( ) ) < maxAllowedDifference );

  }

}
