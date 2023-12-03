package com.openai.chatbot.domain.entity;

/**
 * The role of the messages author. One of system, user, assistant, or function
 */
@SuppressWarnings( { "MissingJavadoc", "HardCodedStringLiteral", "InnerClassOfInterface" } )
public interface ChatMessageRole{

  String name( );

  class System implements ChatMessageRole{

    @Override
    public String name( ){

      return "system";

    }

  }

  class User implements ChatMessageRole{

    @Override
    public String name( ){

      return "user";

    }

  }

  class Assistant implements ChatMessageRole{

    @Override
    public String name( ){

      return "assistant";

    }

  }

  class Function implements ChatMessageRole{

    @Override
    public String name( ){

      return "function";

    }

  }

}