package cz.cvut.fel.nss.careerpages.model;

/**
 * Enum for genders - man, woman
 */
public enum Gender {
    WOMAN("WOMAN"), MAN("MAN");

    private final String gender;

    /**
     * @param gender constructor
     */
    Gender(String gender) {
        this.gender = gender;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return gender;
    }
}

