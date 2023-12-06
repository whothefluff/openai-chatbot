package com.openai.mapstruct;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

/**
 * Overrides the default naming strategy to recognize fluent naming
 */
@SuppressWarnings( { "unused", "ClassWithoutLogger" } )
public class FluentAccessorNamingStrategy extends DefaultAccessorNamingStrategy{

  /*@Override
  public boolean isGetterMethod( final ExecutableElement method ){

    return method.getParameters( ).isEmpty( );

  }

  @Override
  public boolean isSetterMethod( final ExecutableElement method ){

    return method.getParameters( ).size( ) == 1;

  }

  @Override
  public String getPropertyName( final ExecutableElement getterOrSetterMethod ){

    return getterOrSetterMethod.getSimpleName( ).toString( );

  }*/

}