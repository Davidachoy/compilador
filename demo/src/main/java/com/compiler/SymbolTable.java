package com.compiler;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;


public class SymbolTable {
    private Stack<HashMap<String, SymbolInfo>> scopes;
    private ArrayList<HashMap<String, SymbolInfo>> oldScopes;
    private SymbolInfo currentFunction = null;


    public void setCurrentFunction(SymbolInfo currentFunction) {
        this.currentFunction = currentFunction;
    }

    public SymbolTable() {
        scopes = new Stack<>();
        scopes.push(new HashMap<>());  // push a new scope for global variables
        oldScopes = new ArrayList<>();
    }

    public SymbolInfo getCurrentFunction() {
        return currentFunction;
    }

    public void enterFunction(SymbolInfo function) {
        currentFunction = function;
        pushScope();
    }

    public void exitFunction() {
        currentFunction = null;
        popScope();
    }
    
    public void pushScope() {
        scopes.push(new HashMap<>());
    }
    public void popScope() {
        HashMap<String, SymbolInfo> oldScope = scopes.pop();
        oldScopes.add(oldScope);
    }
    public void addSymbol(String identifier, SymbolInfo info) {
        scopes.peek().put(identifier, info);
    }
    public SymbolInfo getSymbol(String identifier) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            HashMap<String, SymbolInfo> scope = scopes.get(i);
            if (scope.containsKey(identifier)) {
                return scope.get(identifier);
            }
        }
        return null;  // not found in any scope
    }


    private void printScopes(ArrayList<HashMap<String, SymbolInfo>> scopesToPrint) {
        int scopeIndex = 0;
        for (HashMap<String, SymbolInfo> scope : scopesToPrint) {
            if (scopeIndex == 0 ) {
                System.out.println("Alcance Global");
                
            }
            for (String key : scope.keySet()) {
                SymbolInfo info = scope.get(key);
                System.out.println("    Identificador: " + key);
                System.out.println("    Tipo: " + info.getType());
                if (info.getType().equals("function")) {
                    List<ParameterInfo> parameters = info.getParameters();
                    System.out.println("    Tipo retorno: " + info.getFunctionType());
                    System.out.println("    Parametros: ");
                    for (ParameterInfo param : parameters) {
                        System.out.println("        Identificador del parámetro: " + param.getName());
                        System.out.println("        Tipo del parámetro: " + param.getType());
                    }
                } else {
                    System.out.println("    Valor: " + info.getValue());
                }

                System.out.println();
            }
            scopeIndex++;
        }
    }


    private String getPrintedScopes(ArrayList<HashMap<String, SymbolInfo>> scopesToPrint) {
        StringBuilder sb = new StringBuilder();
        int scopeIndex = 0;
        for (HashMap<String, SymbolInfo> scope : scopesToPrint) {
            if (scopeIndex == 0 ) {
                sb.append("Alcance Global\n");
            }
            for (String key : scope.keySet()) {
                SymbolInfo info = scope.get(key);
                sb.append("    Identificador: ").append(key).append("\n");
                sb.append("    Tipo: ").append(info.getType()).append("\n");
    
                if (info.getType().equals("function")) {
                    List<ParameterInfo> parameters = info.getParameters();
                    sb.append("    Tipo retorno: ").append(info.getFunctionType()).append("\n");

                    sb.append("    Parametros: \n");
                    for (ParameterInfo param : parameters) {
                        sb.append("        Id: ").append(param.getName());
                        sb.append(" Tipo: ").append(param.getType()).append("\n");
                    }
                } else {
                    sb.append("    Valor: ").append(info.getValue()).append("\n");
                }
    
                sb.append("\n");
            }
            if (scopeIndex == 0 ) {
                sb.append("Alcance local\n");
            }
            scopeIndex++;
        }
        return sb.toString();
    }
    
    
    public void printAllScopes() {
        ArrayList<HashMap<String, SymbolInfo>> allScopes = new ArrayList<>(scopes);
        allScopes.addAll(oldScopes);
        printScopes(allScopes);
    }


    public String getPrintAllScopes() {
        ArrayList<HashMap<String, SymbolInfo>> allScopes = new ArrayList<>(scopes);
        allScopes.addAll(oldScopes);
        return getPrintedScopes(allScopes);
    }

    public SymbolInfo getGlobalSymbol(String identifier) {
        HashMap<String, SymbolInfo> globalScope = scopes.get(0);  // get the global scope
        if (globalScope.containsKey(identifier)) {
            return globalScope.get(identifier);
        }
        return null;  // not found in global scope
    }
    
    
}
