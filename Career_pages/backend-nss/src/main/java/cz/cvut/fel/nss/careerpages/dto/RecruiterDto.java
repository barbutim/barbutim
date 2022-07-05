package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto for recruiter
 */
public class RecruiterDto extends AbstractUserDto{

    @Size(max = 12, min = 9, message = "Phone number is in incorrect format.")
    @NotNull(message = "Phone number cannot be blank")
    private String phone_number;

    @Size(max = 255, min = 1, message = "Company is in incorrect format.")
    @NotNull(message = "Company cannot be blank")
    private String company;

    private List<UserReviewDto> userReviewsAuthor;
    private List<WorkOfferDto> offers;

    /**
     * constructor
     */
    public RecruiterDto() {
        super(Role.MANAGER);
        offers = new ArrayList<>();
        userReviewsAuthor = new ArrayList<>();
    }

    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param email
     * @param address
     * @param phone_number
     * @param company
     * @param userReviewsAuthor
     * @param offers
     * constructor
     */
    public RecruiterDto(@NotNull(message = "Id cannot be blank") Long id, @Size(max = 30, min = 1, message = "First name is in incorrect format.") @NotNull(message = "First name cannot be blank") String firstName,
                        @NotNull(message = "Last name cannot be blank") String lastName, @Email(message = "Email should be valid") @NotNull(message = "Email cannot be blank") String email, AddressDto address, @Size(max = 12, min = 9, message = "Phone number is in incorrect format.") @NotNull(message = "Phone number cannot be blank") String phone_number,
                        @Size(max = 255, min = 1, message = "Company is in incorrect format.") @NotNull(message = "Company cannot be blank") String company, List<UserReviewDto> userReviewsAuthor, List<WorkOfferDto> offers) {

        super(id, firstName, lastName, email, address, Role.MANAGER);
        this.phone_number = phone_number;
        this.company = company;
        this.userReviewsAuthor = userReviewsAuthor;
        this.offers = offers;
    }

    /**
     * @return get offers
     */
    public List<WorkOfferDto> getOffers() {
        return offers;
    }

    /**
     * @param offers
     * set offers
     */
    public void setOffers(List<WorkOfferDto> offers) {
        this.offers = offers;
    }
}
