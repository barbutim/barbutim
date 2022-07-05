package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "APP_MANAGER")
@NamedQueries({
        @NamedQuery(name = "Manager.findByEmail", query = "SELECT m FROM Rectuiter m WHERE m.email = :email AND m.deleted_at is null")
})
public class Rectuiter extends AbstractUser {

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


    public Rectuiter() {
        super(Role.MANAGER);
        userReviewsAuthor = new ArrayList<>();
        offers = new ArrayList<>();
    }

    public Rectuiter(String password, String firstName, String lastName, String email, String phone_number, String company) {
        super(password, firstName, lastName, email, Role.MANAGER);
        this.company = company;
        this.phone_number = phone_number;
        userReviewsAuthor = new ArrayList<>();
        offers = new ArrayList<>();
    }


    public List<UserReview> getUserReviewsAuthor() {

        return userReviewsAuthor;
    }


    public void setUserReviewsAuthor(List<UserReview> userReviewsAuthor) {

        this.userReviewsAuthor = userReviewsAuthor;
    }

    public void addUserReviewAuthor(UserReview userReview){
        userReviewsAuthor.add(userReview);
    }


    public List<Offer> getOffers() {

        return offers;
    }


    public void setOffers(List<Offer> offers) {

        this.offers = offers;
    }


    public String getPhone_number() {

        return phone_number;
    }


    public void setPhone_number(String phone_number) {

        this.phone_number = phone_number;
    }


    public String getCompany() {

        return company;
    }


    public void setCompany(String company) {

        this.company = company;
    }
}
