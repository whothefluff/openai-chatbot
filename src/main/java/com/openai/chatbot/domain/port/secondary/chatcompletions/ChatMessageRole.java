package com.openai.chatbot.domain.port.secondary.chatcompletions;

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