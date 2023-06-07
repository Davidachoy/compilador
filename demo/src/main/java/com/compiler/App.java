package com.compiler;
import java_cup.runtime.Symbol;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("prueba.txt"));
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                Lexer lexer = new Lexer(new BufferedReader(new StringReader(line)));
                List<Symbol> tokens = new ArrayList<Symbol>();
                Symbol symbol = lexer.next_token();
                while (symbol.sym != ParserSym.EOF) {
                    tokens.add(symbol);
                    symbol = lexer.next_token();
                }
                System.out.println("LINEA " + lineNumber + ": " + line);
                for (Symbol s : tokens) {
                    String tokenRepresentation = ParserSym.terminalNames[s.sym];
                    System.out.println("Token: " + tokenRepresentation + ", Value: " + s.value);
                }
                lineNumber++;
            }
            reader.close();

            // Parse the entire file with the parser
            reader = new BufferedReader(new FileReader("prueba.txt"));
            Lexer lexer = new Lexer(reader);
            Parser p = new Parser(lexer);
            p.parse();
            p.printSymbolTable();
            p.action_obj.imprimirCod3D();
            System.out.println("Analisis realizado correctamente.");
            reader.close();
        } catch(Exception e) {
            System.out.println("Ocurrió un error durante el análisis: " + e.getMessage());
        }
    }
}
