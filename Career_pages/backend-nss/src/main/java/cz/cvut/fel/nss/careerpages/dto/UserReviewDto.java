package cz.cvut.fel.nss.careerpages.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Dto for user reviews
 */
public class UserReviewDto {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Size(max = 255, min = 0, message = "Max 255 characters.")
    private String note;

    @NotNull(message = "Date cannot be blank")
    private LocalDateTime date;

    @Min(value = 0, message = "Min 0")
    @Max(value = 5, message = "Max 5")
    private double rating;

    @NotNull(message = "User id cannot be blank")
    private Long userId;

    private Long authorId;
    private JobSessionDto tripSessionDto;

    /**
     * @param id
     * @param note
     * @param date
     * @param rating
     * @param userId
     * @param authorId
     * @param tripSessionDto
     * constructor
     */
    public UserReviewDto(@NotNull(message = "Id cannot be blank") Long id, @NotNull(message = "Note cannot be blank") @Size(max = 255, min = 0, message = "Max 255 characters.") String note,
                         @NotNull(message = "Date cannot be blank") LocalDateTime date, @Min(value = 0, message = "Min 0") @Max(value = 5, message = "Max 5") double rating,
                         @NotNull(message = "User id cannot be blank") Long userId, Long authorId, JobSessionDto tripSessionDto) {
        this.id = id;
        this.note = note;
        this.date = date;
        this.rating = rating;
        this.userId = userId;
        this.authorId = authorId;
        this.tripSessionDto = tripSessionDto;
    }

    /**
     * constructor
     */
    public UserReviewDto() {

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
