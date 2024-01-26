package com.openai.chatbot.application.adapter.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.chatbot.application.adapter.rest.domainintegration.ConversationStarterBody;
import com.openai.chatbot.domain.exception.ChatServiceException;
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
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatServiceExceptionHandlerIT{

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ChatService chatService;

  @Test
  public void handleParentException_ThrownInCreateConversation_ReturnsBadRequest( )
    throws Exception{
    // Arrange
    val errorMessage = "Some 400 error occurred";
    Mockito.when( this.chatService.startConversation( "a", "b" ) ).thenThrow( new ChatServiceException( errorMessage ) );
    val requestBody = new ObjectMapper( ).writeValueAsString( new ConversationStarterBody( ).name( "a" ).systemMessage( "b" ) );
    val call = MockMvcRequestBuilders.post( "/api/v1/conversation" ).contentType( APPLICATION_JSON ).content( requestBody );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isBadRequest( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).isEqualTo( errorMessage );

  }

  @Test
  public void handleConflictException_ThrownInCreateConversation_ReturnsConflict( )
    throws Exception{
    // Arrange
    val convName = "Test name";
    val convMessage = "Test message";
    val errorMessage = "Some 409 error occurred";
    Mockito.when( this.chatService.startConversation( convName, convMessage ) ).thenThrow( new ChatServiceException.Conflict( errorMessage ) );
    val requestBody = new ObjectMapper( ).writeValueAsString( new ConversationStarterBody( ).name( convName ).systemMessage( convMessage ) );
    val call = MockMvcRequestBuilders.post( "/api/v1/conversation" ).contentType( APPLICATION_JSON ).content( requestBody );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isConflict( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).isEqualTo( errorMessage );

  }

  @Test
  public void handleParentException_ThrownInGetConversation_ReturnsBadRequest( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    val errorMessage = "Some 400 error occurred";
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
  public void handleNotFoundException_ThrownInGetConversation_ReturnsNotFound( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    Mockito.when( this.chatService.getConversation( id ) ).thenThrow( new ChatServiceException.NotFound( "Some 404" ) );
    val call = MockMvcRequestBuilders.get( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isNotFound( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).containsIgnoringCase( "not found" );

  }

  @Test
  public void handleParentException_ThrownInDeleteConversation_ReturnsBadRequest( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    val errorMessage = "Some 400 error occurred";
    Mockito.doThrow( new ChatServiceException( errorMessage ) ).when( this.chatService ).deleteConversation( id );
    val call = MockMvcRequestBuilders.delete( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isBadRequest( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).isEqualTo( errorMessage );

  }

  @Test
  public void handleNotFoundException_ThrownInDeleteConversation_ReturnsNotFound( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    Mockito.doThrow( new ChatServiceException.NotFound( "Some 404" ) ).when( this.chatService ).deleteConversation( id );
    val call = MockMvcRequestBuilders.delete( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isNotFound( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).containsIgnoringCase( "not found" );

  }

  @Test
  public void handleConflictException_ThrownInDeleteConversation_ReturnsNotFound( )
    throws Exception{
    // Arrange
    val id = UUID.randomUUID( );
    val errorMessage = "Some 409 error occurred";
    Mockito.doThrow( new ChatServiceException.Conflict( errorMessage ) ).when( this.chatService ).deleteConversation( id );
    val call = MockMvcRequestBuilders.delete( "/api/v1/conversation/{id}", id );
    // Act
    val responseBody = this.mockMvc.perform( call )
                                   .andExpect( status( ).isConflict( ) )
                                   .andReturn( ).getResponse( ).getContentAsString( );
    // Assert
    assertThat( responseBody ).isEqualTo( errorMessage );

  }

}