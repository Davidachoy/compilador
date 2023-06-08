package com.compiler;
import java.util.List;
import java.util.ArrayList;

public class SymbolInfo {
    private String identifier;
    private String type;
    private String functionType;
    private Object value;
    private String valueType;
    
    private List<ParameterInfo> parameters; // nuevo campo

    public String getFunctionType() {
        return functionType;
    }
    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }
    

    public List<ParameterInfo> getParameters() {
        return parameters;
    }
    public void setParameters(List<ParameterInfo> parameters) {
        this.parameters = parameters;

    }
    public SymbolInfo() {
        this.parameters = new ArrayList<>();

    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    public String getValueType() {
        return valueType;
    }
    public void setValueType(String valueType) {
        this.valueType = valueType;
    }


    
}

