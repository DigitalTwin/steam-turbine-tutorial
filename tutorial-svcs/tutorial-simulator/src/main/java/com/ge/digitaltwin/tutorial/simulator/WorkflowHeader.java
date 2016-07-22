package com.ge.digitaltwin.tutorial.simulator;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings({"unused", "WeakerAccess"})
public class WorkflowHeader {

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    private String value;

    public WorkflowHeader() {
        super();
    }

    public WorkflowHeader(String name, String value) {
        this();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
