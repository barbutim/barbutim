package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * dto for applications on offers
 */
public class ApplicantDto extends AbstractUserDto {

    @Size(max = 12, min = 9, message = "Phone number is in incorrect format.")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone_number;

    private JobJournalDto travel_journal;
    private List<JobResponseDto> jobReviews;
    private List<UserReviewDto> userReviewDtos;

    /**
     * constructor
     */
    public ApplicantDto() {
        super(Role.USER);
        userReviewDtos = new ArrayList<>();
        jobReviews = new ArrayList<>();
    }

    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param address
     * @param phone_number
     * @param travel_journal
     * @param tripReviews
     * @param userReviewDtos
     * constructor
     */
    public ApplicantDto(@NotNull(message = "Id cannot be blank") Long id, @Size(max = 30, min = 1, message = "First name is in incorrect format.") @NotNull(message = "First name cannot be blank") String firstName,
                        @NotNull(message = "Last name cannot be blank") String lastName, @Email(message = "Email should be valid") @NotNull(message = "Email cannot be blank") String email, AddressDto address,
                        @Size(max = 12, min = 9, message = "Phone number is in incorrect format.") @NotBlank(message = "Phone number cannot be blank") String phone_number,
                        JobJournalDto travel_journal, List<JobResponseDto> tripReviews, List<UserReviewDto> userReviewDtos) {

        super(id, firstName, lastName, email, address, Role.USER);
        this.phone_number = phone_number;
        this.travel_journal = travel_journal;
        this.jobReviews = tripReviews;
        this.userReviewDtos = userReviewDtos;
    }

    /**
     * @return travel
     */
    public JobJournalDto getTravel_journal() {
        return travel_journal;
    }
}
