package com.compiler;

public class Declaration {
    private Type type;
    
    public Declaration(Type type, String id, Object value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }
    public Declaration(Type type, String id) {
        this(type, id, null);
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    private Object value;
    public Object getValue() {
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }
    

}
