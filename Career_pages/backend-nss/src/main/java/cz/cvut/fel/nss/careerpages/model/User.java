package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for user
 */
@Entity
@Table(name = "APP_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email AND u.deleted_at is null")
})
public class User extends AbstractUser {

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Size(max = 12, min = 9, message = "Phone number is in incorrect format.")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone_number;

    @OneToOne(cascade = CascadeType.ALL)
    private JobJournal travel_journal;

    @OneToMany(mappedBy = "author")
    private List<JobResponse> tripReviews;

    @OneToMany(mappedBy = "user")
    private List<UserReview> userReviews;

    /**
     * constructor
     */
    public User() {
        super(Role.USER);
        this.travel_journal = new JobJournal();
        this.userReviews = new ArrayList<>();
        this.tripReviews = new ArrayList<>();
    }

    /**
     * @param password
     * @param firstName
     * @param lastName
     * @param email
     * @param phone_number
     * constructor
     */
    public User(String password, String firstName, String lastName, String email, String phone_number) {
        super(password,firstName,lastName, email, Role.USER);
        this.travel_journal = new JobJournal();
        this.userReviews = new ArrayList<>();
        this.tripReviews = new ArrayList<>();
        this.phone_number = phone_number;
    }

    /**
     * @return get travel journal
     */
    public JobJournal getTravel_journal() {
        return travel_journal;
    }

    /**
     * @param travel_journal
     * set travel journal
     */
    public void setTravel_journal(JobJournal travel_journal) {
        this.travel_journal = travel_journal;
    }

    /**
     * @return get job reviews
     */
    public List<JobResponse> getJobReviews() {
        return tripReviews;
    }

    /**
     * @param tripReviews
     * set job reviews
     */
    public void setJobReviews(List<JobResponse> tripReviews) {
        this.tripReviews = tripReviews;
    }

    /**
     * @return get user reviews
     */
    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    /**
     * @param tripReview
     * add job review
     */
    public void addJobReview(JobResponse tripReview) {
        tripReviews.add(tripReview);
    }

    /**
     * @param userReview
     * add user review
     */
    public void addUserReview(UserReview userReview){
        this.userReviews.add(userReview);
    }

    /**
     * @return get phone number
     */
    public String getPhone_number() {
        return phone_number;
    }
}
