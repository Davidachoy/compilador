package com.compiler;

import java.io.StringReader;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ParserTest 
{
  @Test
  public void parserAnalysis() throws Exception{
    String expresion ="2*4+6*3";
    Lexer lexer = new Lexer(new StringReader(expresion) );
    Parser p = new Parser(lexer);
    String resultado = (String) p.parse().value;
    System.out.println(resultado);
  }
}
