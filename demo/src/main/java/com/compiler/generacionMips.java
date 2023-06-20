package com.compiler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class generacionMips {

    public static String convertToMIPS(String filePath) throws IOException {
        StringBuilder mipsCode = new StringBuilder();
        mipsCode.append(".data\n");

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("data_int")) {
                String[] parts = line.split(" ");
                String variableName = parts[1];
                mipsCode.append(variableName).append(":  .word 0\n");
            }
        }

        mipsCode.append("\n.text\n.globl main\nmain:\n");

        reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("data") && !line.startsWith("t")) {
                String[] parts = line.split("=");
                String variableName = parts[0].trim();
                String value = parts[1].trim();

                mipsCode.append("    li $t1, ").append(value).append("\n");
                mipsCode.append("    sw $t1, ").append(variableName).append("\n");
            }
        }

        mipsCode.append("\n    li $v0, 10\n    syscall\n");

        reader.close();

        return mipsCode.toString();
    }

}
