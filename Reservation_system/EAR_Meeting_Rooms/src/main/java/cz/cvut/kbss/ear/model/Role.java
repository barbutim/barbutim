package cz.cvut.kbss.ear.model;

public enum Role {
    CUSTOMER("ROLE_CUSTOMER"), EMPLOYEE("ROLE_EMPLOYEE"), ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
