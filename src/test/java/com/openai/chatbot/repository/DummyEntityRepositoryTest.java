package com.openai.chatbot.repository;

import com.openai.chatbot.model.entity.DummyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Data
@AllArgsConstructor( onConstructor = @__( @Autowired ) )
@DataJpaTest
public class DummyEntityRepositoryTest{

  private TestEntityManager entityManager;
  private DummyEntityRepository dummyEntityRepository;

  @Test
  public void whenSave_thenRetrieveSameEntity( ){
    // Given
    val dummyEntity = new DummyEntity( "1" );
    this.entityManager.persist( dummyEntity );
    this.entityManager.flush( );
    // When
    val foundEntity = this.dummyEntityRepository.findById( dummyEntity.getId( ) ).orElse( null );
    // Then
    assertThat( foundEntity ).isNotNull( );
    assertThat( foundEntity.getId( ) ).isEqualTo( dummyEntity.getId( ) );
  }

}
