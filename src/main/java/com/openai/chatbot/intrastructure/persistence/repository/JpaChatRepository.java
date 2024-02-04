package com.openai.chatbot.intrastructure.persistence.repository;

import com.openai.chatbot.intrastructure.persistence.db.model.JpaChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Generic CRUD operations on a JPA repository for {@link JpaChat}
 */
@SuppressWarnings( "InterfaceNeverImplemented" )
public interface JpaChatRepository extends JpaRepository<JpaChat, UUID>{

}
