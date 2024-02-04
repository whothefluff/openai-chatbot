CREATE TABLE chats(
    id BINARY(16) NOT NULL DEFAULT( UUID( ) ) PRIMARY KEY,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    name VARCHAR(255) NULL UNIQUE
);

CREATE TABLE chat_requests(
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    model VARCHAR(255) NOT NULL,
    function_call VARCHAR(255) NULL,
    temperature DECIMAL(6,4) NULL,
    top_p DECIMAL(5,4) NULL,
    n INT NULL,
    stream BIT(1) NULL,
    max_tokens INT NULL,
    presence_penalty DECIMAL(6,4) NULL,
    frequency_penalty DECIMAL(6,4) NULL,
    logit_bias VARCHAR(255) NULL,
    user VARCHAR(255) NULL,
    chat_id BINARY(16) NOT NULL,
    id INT NOT NULL AUTO_INCREMENT,
    previous_response INT NULL,
    PRIMARY KEY( id, chat_id ),
    UNIQUE( chat_id, previous_response )
);

CREATE TABLE chat_responses(
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    object VARCHAR(255) NULL,
    created INT NULL,
    model VARCHAR(255) NULL,
    chat_id BINARY(16) NOT NULL,
    id INT NOT NULL AUTO_INCREMENT,
    previous_request INT NOT NULL,
    PRIMARY KEY( id, chat_id ),
    UNIQUE( chat_id, previous_request )
);

CREATE TABLE chat_response_choices(
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    `index` INT NULL,
    finish_reason VARCHAR(255) NULL,
    chat_id BINARY(16) NOT NULL,
    response_id INT NOT NULL,
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY( id, response_id, chat_id )
);

CREATE TABLE chat_response_choice_messages(
    `role` ENUM( 'system', 'user', 'assistant', 'function' ) NOT NULL,
    content VARCHAR(255) NULL,
    chat_id BINARY(16) NOT NULL,
    response_id INT NOT NULL,
    choice_id INT NOT NULL,
    PRIMARY KEY( chat_id, response_id, choice_id )
);

CREATE TABLE chat_response_choice_message_function_calls(
    name VARCHAR(255) NOT NULL,
    arguments VARCHAR(255) NOT NULL,
    chat_id BINARY(16) NOT NULL,
    response_id INT NOT NULL,
    choice_id INT NOT NULL,
    PRIMARY KEY( chat_id, response_id, choice_id )
);

CREATE TABLE chat_response_usages(
    prompt_tokens INT NULL,
    completion_tokens INT NULL,
    total_tokens INT NULL,
    chat_id BINARY(16) NOT NULL,
    response_id INT NOT NULL,
    PRIMARY KEY( chat_id, response_id )
);

CREATE TABLE chat_request_function_definitions(
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    name VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    parameters VARCHAR(255) NOT NULL,
    chat_id BINARY(16) NOT NULL,
    request_id INT NOT NULL,
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY( id, request_id, chat_id )
);

CREATE TABLE chat_request_messages(
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    `role` ENUM( 'system', 'user', 'assistant', 'function' ) NOT NULL,
    content VARCHAR(255) NULL,
    name VARCHAR(255) NULL,
    chat_id BINARY(16) NOT NULL,
    request_id INT NOT NULL,
    id INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY( id, request_id, chat_id )
);

CREATE TABLE chat_request_message_function_calls(
    name VARCHAR(255) NOT NULL,
    arguments VARCHAR(255) NOT NULL,
    chat_id BINARY(16) NOT NULL,
    request_id INT NOT NULL,
    message_id INT NOT NULL,
    PRIMARY KEY( chat_id, request_id, message_id )
);

ALTER TABLE chat_request_message_function_calls
    ADD CONSTRAINT FK_RMFC_ON_CHAT_REQUEST_MESSAGE FOREIGN KEY (message_id, request_id, chat_id) REFERENCES chat_request_messages( id, request_id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_request_messages
    ADD CONSTRAINT FK_RM_ON_CHAT_REQUEST FOREIGN KEY (request_id,chat_id) REFERENCES chat_requests( id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_request_function_definitions
    ADD CONSTRAINT FK_RFD_ON_CHAT_REQUEST FOREIGN KEY (request_id,chat_id) REFERENCES chat_requests( id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_response_usages
    ADD CONSTRAINT FK_RU_ON_CHAT_RESPONSE FOREIGN KEY (response_id, chat_id) REFERENCES chat_responses( id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_response_choice_messages
    ADD CONSTRAINT FK_RCM_ON_CHAT_RESPONSE_CHOICE FOREIGN KEY ( choice_id, response_id, chat_id ) REFERENCES chat_response_choices( id, response_id, chat_id  )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_response_choices
    ADD CONSTRAINT FK_RC_ON_CHAT_RESPONSE FOREIGN KEY (response_id, chat_id) REFERENCES chat_responses( id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_response_choice_message_function_calls
    ADD CONSTRAINT FK_RCMFC_ON_RESPONSE_CHOICE_MESSAGE FOREIGN KEY (choice_id, response_id, chat_id) REFERENCES chat_response_choice_messages( choice_id, response_id, chat_id )
	ON DELETE CASCADE
	ON UPDATE CASCADE;

ALTER TABLE chat_requests
    ADD CONSTRAINT FK_R_ON_CHAT_RESPONSE FOREIGN KEY (previous_response, chat_id ) REFERENCES chat_responses( id, chat_id );

ALTER TABLE chat_requests
    ADD CONSTRAINT FK_REQ_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chats( id );

ALTER TABLE chat_responses
    ADD CONSTRAINT FK_R_ON_CHAT_REQUEST FOREIGN KEY (previous_request, chat_id) REFERENCES chat_requests( id, chat_id );

ALTER TABLE chat_responses
    ADD CONSTRAINT FK_RES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chats( id );
    
CREATE TABLE chat_requests_seq( next_val BIGINT );
INSERT INTO chat_requests_seq( next_val ) VALUES( 1 );
CREATE TABLE chat_responses_seq( next_val BIGINT );
INSERT INTO chat_responses_seq( next_val ) VALUES( 1 );
CREATE TABLE chat_request_function_definitions_seq( next_val BIGINT );
INSERT INTO chat_request_function_definitions_seq( next_val ) VALUES( 1 );
CREATE TABLE chat_request_messages_seq( next_val BIGINT );
INSERT INTO chat_request_messages_seq( next_val ) VALUES( 1 );
CREATE TABLE chat_response_choices_seq( next_val BIGINT );
INSERT INTO chat_response_choices_seq( next_val ) VALUES( 1 );