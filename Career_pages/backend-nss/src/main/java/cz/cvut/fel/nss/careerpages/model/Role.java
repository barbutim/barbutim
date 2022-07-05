package cz.cvut.fel.nss.careerpages.model;

/**
 * Enum for roles in the system - user, admin, manager
 */
public enum Role {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN"), MANAGER("ROLE_MANAGER");

    private final String role;

    /**
     * @param role
     * constructor
     */
    Role(String role) {
        this.role = role;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return role;
    }
}

