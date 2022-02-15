package cz.cvut.kbss.ear.model;

public enum Status {
    ACTIVE("STATUS_ACTIVE"), CANCELED("STATUS_CANCELED"), PAID("STATUS_PAID"), REFUNDED("STATUS_REFUNDED");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
