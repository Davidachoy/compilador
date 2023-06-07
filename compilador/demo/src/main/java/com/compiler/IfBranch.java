package com.compiler;

public class IfBranch {
    public String endIfLabel;

    public String getEndIfLabel() {
        return endIfLabel;
    }

    public void setEndIfLabel(String endIfLabel) {
        this.endIfLabel = endIfLabel;
    }

    public IfBranch(String endIfLabel) {
        this.endIfLabel = endIfLabel;
    }
}
