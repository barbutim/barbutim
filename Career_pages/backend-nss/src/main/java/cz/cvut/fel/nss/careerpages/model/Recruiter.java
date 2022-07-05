package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for recruiter
 */
@Entity
@Table(name = "APP_MANAGER")
@NamedQueries({
        @NamedQuery(name = "Manager.findByEmail", query = "SELECT m FROM Recruiter m WHERE m.email = :email AND m.deleted_at is null")
})
public class Recruiter extends AbstractUser {

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Size(max = 12, min = 9, message = "Phone number is in incorrect format.")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone_number;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 255, min = 1, message = "Company is in incorrect format.")
    @NotBlank(message = "Company cannot be blank")
    private String company;

    @OneToMany(mappedBy = "author")
    private List<UserReview> userReviewsAuthor;

    @OneToMany(mappedBy = "author")
    private List<Offer> offers;

    /**
     * constructor
     */
    public Recruiter() {
        super(Role.MANAGER);
        userReviewsAuthor = new ArrayList<>();
        offers = new ArrayList<>();
    }

    /**
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param phone_number
     * @param company
     * constructor
     */
    public Recruiter(String password, String firstName, String lastName, String email, String phone_number, String company) {
        super(password, firstName, lastName, email, Role.MANAGER);
        this.company = company;
        this.phone_number = phone_number;
        userReviewsAuthor = new ArrayList<>();
        offers = new ArrayList<>();
    }

    /**
     * @return get user reviews
     */
    public List<UserReview> getUserReviewsAuthor() {
        return userReviewsAuthor;
    }

    /**
     * @param userReview
     * add user reviews
     */
    public void addUserReviewAuthor(UserReview userReview){
        userReviewsAuthor.add(userReview);
    }

    /**
     * @return get offers
     */
    public List<Offer> getOffers() {
        return offers;
    }

    /**
     * @param offers
     * set offers
     */
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    /**
     * @return get phone number
     */
    public String getPhone_number() {
        return phone_number;
    }

    /**
     * @return get company
     */
    public String getCompany() {
        return company;
    }
}
