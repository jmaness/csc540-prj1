package edu.ncsu.csc540.health.model;

public enum Operation {
    LESS_THAN("<"),
    LESS_THAN_EQUAL_TO("<="),
    EQUAL_TO("="),
    GREATER_THAN_EQUAL_TO(">="),
    GREATER_THAN(">");

    private String name;

    Operation(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
