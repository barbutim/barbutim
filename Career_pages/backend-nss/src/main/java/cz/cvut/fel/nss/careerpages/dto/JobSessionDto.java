package cz.cvut.fel.nss.careerpages.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Dto for job session
 */
public class JobSessionDto implements Serializable {

    private Long id;

    private LocalDate from_date;

    private LocalDate to_date;

    private int capacity;

    private Long tripDtoId;

    /**
     * @param id
     * @param from_date
     * @param to_date
     * @param capacity
     * @param tripDtoId
     * constructor
     */
    public JobSessionDto(@NotNull(message = "Id cannot be blank") Long id, @FutureOrPresent LocalDate from_date, @FutureOrPresent LocalDate to_date, @Min(value = 1, message = "Min 1") @Max(value = 500, message = "Max 500") int capacity,
                          Long tripDtoId) {
        this.id = id;
        this.from_date = from_date;
        this.to_date = to_date;
        this.capacity = capacity;
        this.tripDtoId = tripDtoId;
    }

    /**
     * constructor
     */
    public JobSessionDto() {

    }

    /**
     * @return get from date
     */
    public LocalDate getFrom_date() {
        return from_date;
    }

    /**
     * @return get to date
     */
    public LocalDate getTo_date() {
        return to_date;
    }

    /**
     * @return get capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return get id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     * set id
     */
    public void setId(Long id) {
        this.id = id;
    }
}
