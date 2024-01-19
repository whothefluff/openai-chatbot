package com.openai.chatbot.application.adapter.rest;

import com.openai.chatbot.domain.exception.ChatServiceException;
import com.openai.chatbot.domain.exception.ChatServiceNotFoundException;
import com.openai.chatbot.domain.port.primary.ChatService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatServiceExceptionHandlerIT{

  //TODO test all methods in controller
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ChatService chatService;

  @Test
  public void handleParentException_ChatServiceExInGetConversation_ReturnsBadRequest( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    val errorMessage = "Some error occurred";
    Mockito.when( this.chatService.getConversation( id ) ).thenThrow( new ChatServiceException( errorMessage ) );
    val call = MockMvcRequestBuilders.get( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isBadRequest( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).isEqualTo( errorMessage );

  }

  @Test
  public void handleNotFoundException_ChatServiceNotFoundExInGetConversation_ReturnsNotFound( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    Mockito.when( this.chatService.getConversation( id ) ).thenThrow( new ChatServiceNotFoundException( "lelolelolelo" ) );
    val call = MockMvcRequestBuilders.get( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isNotFound( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).containsIgnoringCase( "not found" );

  }

}