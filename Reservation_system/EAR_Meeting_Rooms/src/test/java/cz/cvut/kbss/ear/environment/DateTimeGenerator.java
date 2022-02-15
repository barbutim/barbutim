package cz.cvut.kbss.ear.environment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class DateTimeGenerator {
    private static final Random RAND = new Random();

    public static LocalDate generateDate() {
        int day = RAND.nextInt(28) + 1;
        int month = RAND.nextInt(11) + 1;
        int year = RAND.nextInt(2) + 2019;

        return LocalDate.of(year, month, day);
    }

    public static LocalDateTime generateDateTime() {
        int day = RAND.nextInt(28) + 1;
        int month = RAND.nextInt(11) + 1;
        int year = RAND.nextInt(2) + 2019;
        int hour = RAND.nextInt(24);
        int minute = RAND.nextInt(60);
        int second = RAND.nextInt(60);

        return LocalDateTime.of(year, month, day, hour, minute, second);
    }
}
