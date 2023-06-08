package com.compiler;
import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;
public class SymbolTable {
    private Stack<HashMap<String, SymbolInfo>> scopes;
    private ArrayList<HashMap<String, SymbolInfo>> oldScopes;

    public SymbolTable() {
        scopes = new Stack<>();
        scopes.push(new HashMap<>());  // push a new scope for global variables
        oldScopes = new ArrayList<>();
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
                System.out.println("    Valor: " + info.getValue());
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
                sb.append("    Valor: ").append(info.getValue()).append("\n\n");
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
