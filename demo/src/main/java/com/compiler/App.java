package com.compiler;
import java_cup.runtime.Symbol;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        String archivo = "prueba.txt";
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                Lexer lexer = new Lexer(new BufferedReader(new StringReader(line)));
                List<Symbol> tokens = new ArrayList<Symbol>();
                Symbol symbol = lexer.next_token();
                while (symbol.sym != ParserSym.EOF) {
                    //prueba
                    tokens.add(symbol);
                    symbol = lexer.next_token();
                }
            
                outputBuilder.append("LINEA ").append(lineNumber).append("\n");
                for (Symbol s : tokens) {
                    String tokenRepresentation = ParserSym.terminalNames[s.sym];
                    outputBuilder.append("Token: ").append(tokenRepresentation).append(", Value: ").append(s.value).append("\n");
                }
            
                lineNumber++;
            }
            
            reader.close();

            // Parse the entire file with the parser
            reader = new BufferedReader(new FileReader(archivo));
            Lexer lexer = new Lexer(reader);
            Parser p = new Parser(lexer);
            p.parse();

            System.out.println("Analisis finalizado.");
            

            String cod3DOutput = p.action_obj.getImprimirCod3DOutput();
            String symbolTableOutput = p.getSymbolTable();
            String sintactiOutput = outputBuilder.toString();

            writeToFile(symbolTableOutput, "Tabla de simbolos.txt");
            writeToFile(cod3DOutput, "Codigo3D.txt");
            writeToFile(sintactiOutput, "Analisis.txt");

            reader.close();
        } catch(Exception e) {
            System.out.println("Ocurrió un error durante el análisis: " + e.getMessage());
        }
    }
    
    private static void writeToFile(String content, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(content);
            writer.close();
            System.out.println("Archivo creado exitosamente: " + filename);
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el archivo: " + e.getMessage());
        }
    }
}
