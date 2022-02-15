package cz.cvut.kbss.ear.environment;

import cz.cvut.kbss.ear.model.Role;
import cz.cvut.kbss.ear.model.Status;
import cz.cvut.kbss.ear.model.Time;

import java.util.Random;

public class EnumGenerator {

    private static final Random RAND = new Random();

    public static Role generateRole() {
        return Role.values()[RAND.nextInt(Role.values().length)];
    }

    public static Status generateStatus() {
        return Status.values()[RAND.nextInt(Status.values().length)];
    }

    public static Time generateTime() {
        return Time.values()[RAND.nextInt(Time.values().length)];
    }
}
