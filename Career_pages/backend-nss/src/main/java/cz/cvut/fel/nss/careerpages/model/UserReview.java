package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Model for user review
 */
@Entity
@Table(name = "USER_REVIEW")
public class UserReview extends AbstractEntity {

    @Size(max = 255, min = 0, message = "Max 255 characters.")
    private String note;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime date;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 5, message = "Max 5")
    private double rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Recruiter author;

    @ManyToOne
    @JoinColumn(name = "trip_session_id", nullable = false)
    private JobSession tripSession;

    /**
     * @param note
     * @param date
     * @param rating
     * @param user
     * @param author
     * @param tripSession
     * constructor
     */
    public UserReview(@Size(max = 255, min = 0, message = "Max 255 characters.") String note, LocalDateTime date,
                      @Min(value = 0, message = "Min 0") @Max(value = 5, message = "Max 5") double rating,
                      User user, Recruiter author, JobSession tripSession) {
        this.note = note;
        this.date = date;
        this.rating = rating;
        this.setUser(user);
        this.setAuthor(author);
        this.tripSession = tripSession;
    }

    /**
     * constructor
     */
    public UserReview() {
        this.date = LocalDateTime.now();
    }

    /**
     * @return get note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     * set note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return get date
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date
     * set date
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * @return get rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * @param rating
     * set rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return get user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user
     * set user
     */
    public void setUser(User user) {
        user.addUserReview(this);
        this.user = user;
    }

    /**
     * @return get author
     */
    public Recruiter getAuthor() {
        return author;
    }

    /**
     * @param author
     * set author
     */
    public void setAuthor(Recruiter author) {
        author.addUserReviewAuthor(this);
        this.author = author;
    }

    /**
     * @return get trip session
     */
    public JobSession getTripSession() {
        return tripSession;
    }

    /**
     * @param tripSession
     * set trip session
     */
    public void setTripSession(JobSession tripSession) {
        this.tripSession = tripSession;
    }
}
