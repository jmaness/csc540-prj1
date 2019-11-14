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

    public static Operation fromString(String operation) {
        switch (operation) {
            case "<":
                return LESS_THAN;
            case "<=":
                return LESS_THAN_EQUAL_TO;
            case "=":
                return EQUAL_TO;
            case ">=":
                return GREATER_THAN_EQUAL_TO;
            case ">":
                return GREATER_THAN;
            default:
                return null;
        }
    }
}
