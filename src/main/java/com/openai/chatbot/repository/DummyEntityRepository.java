package com.openai.chatbot.repository;

import com.openai.chatbot.model.entity.DummyEntity;
import org.springframework.data.repository.CrudRepository;

public interface DummyEntityRepository extends CrudRepository<DummyEntity, String>{

}
