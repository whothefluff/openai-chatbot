package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@SuppressWarnings( { "MissingJavadoc", "InterfaceNeverImplemented" } )
public interface JpaChatJpaRepo extends JpaRepository<JpaChat, UUID>{

}
