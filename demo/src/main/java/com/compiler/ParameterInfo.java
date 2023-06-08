package com.compiler;

public class ParameterInfo {
    
    public ParameterInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    private String name;

    private String type;

    public ParameterInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
