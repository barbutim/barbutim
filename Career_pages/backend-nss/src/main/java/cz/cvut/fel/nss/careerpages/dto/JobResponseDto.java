package cz.cvut.fel.nss.careerpages.dto;

import javax.persistence.Basic;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * dto for responding on jobs
 */
public class JobResponseDto implements Serializable {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @Size(max = 255, min = 0, message = "Max 255 characters.")
    private String note;

    @Basic(optional = false)
    private LocalDateTime date;

    @Basic(optional = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 5, message = "Max 5")
    private double rating;

    private String author;

    /**
     * @param id
     * @param note
     * @param date
     * @param rating
     * @param author
     * constructor
     */
    public JobResponseDto(@NotNull(message = "Id cannot be blank") Long id, @Size(max = 255, min = 0, message = "Max 255 characters.") String note,
                          LocalDateTime date, @Min(value = 0, message = "Min 0") @Max(value = 5, message = "Max 5") double rating, String author) {
        this.id = id;
        this.note = note;
        this.date = date;
        this.rating = rating;
        this.author = author;
    }

    /**
     * constructor
     */
    public JobResponseDto() {
    }
}
