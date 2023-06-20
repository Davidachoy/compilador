package com.compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class generacionMips {

    public static String convertirTresDireccionesAMIPS(String rutaArchivo) {
        StringBuilder codigoMIPS = new StringBuilder();

        // Set to keep track of unique variables
        Set<String> variables = new HashSet<>();

        // Read the content of the file
        String codigo3Direcciones;
        try {
            codigo3Direcciones = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        // Extract variables from the three-address code
        String[] lineas = codigo3Direcciones.split("\n");
        for (String linea : lineas) {
            linea = linea.trim();

            if (linea.isEmpty() || !linea.contains("=")) {
                continue;
            }

            String variable = linea.split("=")[0].trim();
            variables.add(variable);
        }

        // Generate .data section
        codigoMIPS.append(".data\n");
        for (String variable : variables) {
            codigoMIPS.append(variable).append(": .word 0\n");
        }
        codigoMIPS.append("\n.text\n");
        codigoMIPS.append(".globl main\n");
        codigoMIPS.append("main:\n");

        // Generate MIPS code for each line
        for (String linea : lineas) {
            linea = linea.trim();

            if (linea.isEmpty()) {
                continue;
            }

            if (linea.contains("=")) {
                String[] partes = linea.split("=");

                String destino = partes[0].trim();
                String expresion = partes[1].trim();

                if (expresion.contains("+")) {
                    String[] operandos = expresion.split("\\+");
                    String operando1 = operandos[0].trim();
                    String operando2 = operandos[1].trim();

                    codigoMIPS.append("    lw $t1, ").append(operando1).append("\n");
                    codigoMIPS.append("    lw $t2, ").append(operando2).append("\n");
                    codigoMIPS.append("    add $t0, $t1, $t2\n");
                    codigoMIPS.append("    sw $t0, ").append(destino).append("\n");
                } else if (expresion.contains("-")) {
                    String[] operandos = expresion.split("-");
                    String operando1 = operandos[0].trim();
                    String operando2 = operandos[1].trim();

                    codigoMIPS.append("    lw $t1, ").append(operando1).append("\n");
                    codigoMIPS.append("    lw $t2, ").append(operando2).append("\n");
                    codigoMIPS.append("    sub $t0, $t1, $t2\n");
                    codigoMIPS.append("    sw $t0, ").append(destino).append("\n");
                } else if (expresion.contains("*")) {
                    String[] operandos = expresion.split("\\*");
                    String operando1 = operandos[0].trim();
                    String operando2 = operandos[1].trim();

                    codigoMIPS.append("    lw $t1, ").append(operando1).append("\n");
                    codigoMIPS.append("    lw $t2, ").append(operando2).append("\n");
                    codigoMIPS.append("    mult $t1, $t2\n");
                    codigoMIPS.append("    mflo $t0\n");
                    codigoMIPS.append("    sw $t0, ").append(destino).append("\n");
                } else if (expresion.contains("/")) {
                    String[] operandos = expresion.split("/");
                    String operando1 = operandos[0].trim();
                    String operando2 = operandos[1].trim();

                    codigoMIPS.append("    lw $t1, ").append(operando1).append("\n");
                    codigoMIPS.append("    lw $t2, ").append(operando2).append("\n");
                    codigoMIPS.append("    div $t1, $t2\n");
                    codigoMIPS.append("    mflo $t0\n");
                    codigoMIPS.append("    sw $t0, ").append(destino).append("\n");
                } else {
                    codigoMIPS.append("    li $t0, ").append(expresion).append("\n");
                    codigoMIPS.append("    sw $t0, ").append(destino).append("\n");
                }
            }
        }

        return codigoMIPS.toString();
    }

}
