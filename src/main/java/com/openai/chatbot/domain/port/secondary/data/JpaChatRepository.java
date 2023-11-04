package com.openai.chatbot.domain.port.secondary.data;

import com.openai.chatbot.intrastructure.persistence.db.JpaChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//TODO move?
public interface JpaChatRepository extends JpaRepository<JpaChat, UUID>{

}
