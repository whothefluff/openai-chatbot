package com.openai.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ChatbotApplication{

  public static void main( final String[] args ){
    //SpringApplication.run( ChatbotApplication.class, args );
    SpringApplication app = new SpringApplication( ChatbotApplication.class );
    app.setDefaultProperties( Collections.singletonMap( "spring.jpa.show-sql", "true" ) );
    app.run( args );

  }

}
