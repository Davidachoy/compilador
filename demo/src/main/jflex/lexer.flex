package com.compiler;
import java_cup.runtime.*;
import java.util.List;
import java.util.ArrayList;


%%

%class Lexer
%cup
%implements ParserSym
%line
%column
%char
%unicode


%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
    }

    List<Symbol> tokens = new ArrayList<>();

    public List<Symbol> getTokens() {
    return tokens;
    
    }
%}


%eofval{
    return symbol(ParserSym.EOF);
%eofval}

WhiteSpace      = [ \t\r\n]+
InputCharacter  = [^\r\n]
LineTerminator = \r|\n|\r\n

Identifier      = [a-zA-Z_] [a-zA-Z0-9_]*

IntegerLiteral  = -?[0-9]+
FloatLiteral    = -?[0-9]+\.[0-9]+

Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}

TraditionalComment   = "/_" [^_] ~"_/" | "/_" "_"+ "/"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = "@" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/" {CommentContent} "_"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

%state STRING
%state COMMENT


%%

<YYINITIAL> {
    /* keywords */
    "if"     { Symbol s = symbol(ParserSym.IF, yytext()); tokens.add(s); return s; }
    "else"   { Symbol s = symbol(ParserSym.ELSE, yytext()); tokens.add(s); return s; }
    "while"  { Symbol s = symbol(ParserSym.WHILE, yytext()); tokens.add(s); return s; }
    "for"    { Symbol s = symbol(ParserSym.FOR, yytext()); tokens.add(s); return s; }
    "int"    { Symbol s = symbol(ParserSym.INT, yytext()); tokens.add(s); return s; }
    "return" { Symbol s = symbol(ParserSym.RETURN, yytext()); tokens.add(s); return s; }
    "float"  { Symbol s = symbol(ParserSym.FLOAT, yytext()); tokens.add(s); return s; }
    "bool"   { Symbol s = symbol(ParserSym.BOOL, yytext()); tokens.add(s); return s; }
    "char"   { Symbol s = symbol(ParserSym.CHAR, yytext()); tokens.add(s); return s; }
    "string" { Symbol s = symbol(ParserSym.STRING, yytext()); tokens.add(s); return s; }
    "elif"   { Symbol s = symbol(ParserSym.ELIF, yytext()); tokens.add(s); return s; }
    "do"     { Symbol s = symbol(ParserSym.DO, yytext()); tokens.add(s); return s; }
    "read"   { Symbol s = symbol(ParserSym.READ, yytext()); tokens.add(s); return s; }
    "print"  { Symbol s = symbol(ParserSym.PRINT, yytext()); tokens.add(s); return s; }
    "main"   { Symbol s = symbol(ParserSym.MAIN, yytext()); tokens.add(s); return s; }
    "true"   { Symbol s = symbol(ParserSym.TRUE, yytext()); tokens.add(s); return s; }
    "break"  { Symbol s = symbol(ParserSym.BREAK, yytext()); tokens.add(s); return s; }
    "false"  { Symbol s = symbol(ParserSym.FALSE, yytext()); tokens.add(s); return s; }
    "$"      { Symbol s = symbol(ParserSym.DOLAR, yytext()); tokens.add(s); return s; }
    "not"    { Symbol s = symbol(ParserSym.NOT, yytext()); tokens.add(s); return s; }
    "!"      { Symbol s = symbol(ParserSym.NOT, yytext()); tokens.add(s); return s; }

    /* identifiers */
    {Identifier}        { Symbol s = symbol(ParserSym.IDENTIFIER, yytext()); tokens.add(s); return s; }

    /* literals */
    {IntegerLiteral}    { Symbol s = symbol(ParserSym.INTEGER_LITERAL, Integer.parseInt(yytext())); tokens.add(s); return s; }
    {FloatLiteral}      { Symbol s = symbol(ParserSym.FLOAT_LITERAL, Float.parseFloat(yytext())); tokens.add(s); return s; }
    \'[^\\\']\'         { Symbol s = symbol(ParserSym.CHAR_LITERAL, yytext().charAt(1)); tokens.add(s); return s; } // 'a', 'b', etc.
    \" { yybegin(STRING); string.setLength(0); } // Comienzo del string

    /* operators and separators */
    "="     { Symbol s = symbol(ParserSym.EQ, yytext()); tokens.add(s); return s; }
    "=="    { Symbol s = symbol(ParserSym.EQEQ, yytext()); tokens.add(s); return s; }
    "+"     { Symbol s = symbol(ParserSym.PLUS, yytext()); tokens.add(s); return s; }
    "-"     { Symbol s = symbol(ParserSym.MINUS, yytext()); tokens.add(s); return s; }
    "*"     { Symbol s = symbol(ParserSym.MUL, yytext()); tokens.add(s); return s; }
    "/"     { Symbol s = symbol(ParserSym.DIV, yytext()); tokens.add(s); return s; }
    "("     { Symbol s = symbol(ParserSym.LPAREN, yytext()); tokens.add(s); return s; }
    ")"     { Symbol s = symbol(ParserSym.RPAREN, yytext()); tokens.add(s); return s; }
    "{"     { Symbol s = symbol(ParserSym.LBRACE, yytext()); tokens.add(s); return s; }
    "}"     { Symbol s = symbol(ParserSym.RBRACE, yytext()); tokens.add(s); return s; }
    "++"    { Symbol s = symbol(ParserSym.INCREMENT, yytext()); tokens.add(s); return s; }
    "--"    { Symbol s = symbol(ParserSym.DECREMENT, yytext()); tokens.add(s); return s; }
    "["     { Symbol s = symbol(ParserSym.LBRACKET, yytext()); tokens.add(s); return s; }
    "]"     { Symbol s = symbol(ParserSym.RBRACKET, yytext()); tokens.add(s); return s; }
    "**"    { Symbol s = symbol(ParserSym.POWER, yytext()); tokens.add(s); return s; }
    "~"     { Symbol s = symbol(ParserSym.MODULO, yytext()); tokens.add(s); return s; }
    "<"     { Symbol s = symbol(ParserSym.LESS_THAN, yytext()); tokens.add(s); return s; }
    "<="    { Symbol s = symbol(ParserSym.LESS_THAN_EQUAL, yytext()); tokens.add(s); return s; }
    ">"     { Symbol s = symbol(ParserSym.GREATER_THAN, yytext()); tokens.add(s); return s; }
    ">="    { Symbol s = symbol(ParserSym.GREATER_THAN_EQUAL, yytext()); tokens.add(s); return s; }
    "!="    { Symbol s = symbol(ParserSym.NOT_EQUAL, yytext()); tokens.add(s); return s; }
    "^"     { Symbol s = symbol(ParserSym.AND, yytext()); tokens.add(s); return s; }
    "#"     { Symbol s = symbol(ParserSym.OR, yytext()); tokens.add(s); return s; }
    ","     { Symbol s = symbol(ParserSym.COMMA, yytext()); tokens.add(s); return s; }



    /* comments (C-style) */
    "/_"              { yybegin(COMMENT); }
    "@"               { /* manejar comentario de línea */ }

    /* whitespace */
    {WhiteSpace}      { /* ignore whitespace */ }
    {Comment}         { /* ignore Comments */ }
}

<COMMENT> {
    "_/"             { yybegin(YYINITIAL); }
    .                { /* ignore other characters */ }
}
<STRING> {
    \\n  { string.append('\n'); } // Secuencia de escape de nueva línea
    \\t  { string.append('\t'); } // Secuencia de escape de tabulación
    \\\" { string.append('\"'); } // Secuencia de escape de comillas
    [^\n\r\\\"]+ { string.append(yytext()); } // Caracteres regulares
    \"   { yybegin(YYINITIAL); return symbol(ParserSym.STRING_LITERAL, string.toString()); } // Fin del string
}

. {
    throw new Error("Carácter ilegal <" + yytext() + "> en línea " + yyline + ", columna " + yycolumn);
}
