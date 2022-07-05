package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for job offer
 */
@Entity
@Table(name = "OFFER")
@NamedQueries({
        @NamedQuery(name = "Offer.findByStringId", query = "SELECT t FROM Offer t WHERE t.short_name = :id AND t.deleted_at is null"),
        @NamedQuery(name = "Offer.findByLevel", query = "SELECT t FROM Offer t WHERE t.required_level <= :required_level AND t.deleted_at is null"),

        @NamedQuery(name = "Offer.findByFilter", query = "SELECT DISTINCT t FROM Offer t JOIN t.sessions s WHERE (" +
                "(:location is null OR t.location = :location) AND " +
                "(:minPrice is null OR t.salary >= :minPrice) AND " +
                "(s.from_date >= :from_date) AND " +
                "(s.to_date <= :to_date))"),

        @NamedQuery(name = "Offer.findByPattern", query = "SELECT DISTINCT t FROM Offer t WHERE (" +
                "(t.id IN :ids) AND " +
                "(t.description LIKE :pattern ) OR " +
                "(t.name LIKE :pattern))"),
})
public class Offer extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 255, min = 3, message = "Name has to be from 3 to 255 characters.")
    @NotBlank(message = "Name has to be from 3 to 255 characters.")
    private String name;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @Size(max = 100, min = 3, message = "Short name has to be from 3 to 100 characters.")
    @NotBlank(message = "Short name has to be from 3 to 100 characters.")
    private String short_name;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 20, message = "Max 20")
    private int possible_xp_reward;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 3000, min = 0, message = "Max 3000 characters.")
    private String description;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 5, message = "Max 5")
    private double rating;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 10000, message = "Max 10 000")
    private double salary;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 200, min = 0, message = "Max 200 characters.")
    private String location;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 0, message = "Min 0")
    private int required_level;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Recruiter author;

    @ManyToMany
    @JoinTable(
            name = "required_achievement_certificate_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_certificate_id"))
    private List<AchievementCertificate> required_achievements_certificate;

    @ManyToMany
    @JoinTable(
            name = "required_achievement_special_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_special_id"))
    private List<AchievementSpecial> required_achievements_special;

    @ManyToMany
    @JoinTable(
            name = "required_achievement_categorized_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_categorized_id"))
    private List<AchievementCategorized> required_achievements_categorized;

    @ManyToMany
    @JoinTable(
            name = "gain_achievement_trip",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id"))
    private List<AchievementSpecial> gain_achievements_special;

    @OrderBy("from_date ASC")
    @OneToMany(mappedBy = "trip")
    private List<JobSession> sessions;

    @OneToMany(mappedBy = "trip")
    private List<JobResponse> jobResponses;

    /**
     * constructor
     */
    public Offer() {
        this.required_achievements_categorized = new ArrayList<>();
        this.required_achievements_special = new ArrayList<>();
        this.required_achievements_certificate = new ArrayList<>();
        this.gain_achievements_special = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }

    /**
     * @param name
     * @param possible_xp_reward
     * @param description
     * @param short_name
     * @param salary
     * @param location
     * @param required_level
     * @param author
     * constructor
     */
    public Offer(@Size(max = 255, min = 3, message = "Name has to be from 3 to 255 characters.")  @NotBlank(message = "Name has to be from 3 to 255 characters.") String name,
                @Min(value = 0, message = "Min 0") @Max(value = 20, message = "Max 20") int possible_xp_reward,
                @Size(max = 3000, min = 0, message = "Max 3000 characters.") String description,
                @Size(max = 100, min = 3, message = "Short name has to be from 3 to 100 characters.")  @NotBlank(message = "Short name has to be from 3 to 100 characters.") String short_name,
                @Min(value = 0, message = "Min 0") @Max(value = 10000, message = "Max 10 000") double salary,
                @Size(max = 200, min = 0, message = "Max 200 characters.") String location,
                @Min(value = 0, message = "Min 0") @Max(value = 100, message = "Max 100") int required_level,
                 Recruiter author
    ) {
        this.name = name;
        this.salary = salary;
        this.possible_xp_reward = possible_xp_reward;
        this.description = description;
        this.location= location;
        this.required_level = required_level;
        this.short_name = short_name;
        this.required_achievements_categorized = new ArrayList<>();
        this.required_achievements_special = new ArrayList<>();
        this.required_achievements_certificate = new ArrayList<>();
        this.gain_achievements_special = new ArrayList<>();
        this.sessions = new ArrayList<>();
        this.author = author;
    }

    /**
     * @param category
     * set category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return get category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @return get name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return get xp
     */
    public Integer getPossible_xp_reward() {
        return possible_xp_reward;
    }

    /**
     * @return get description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     * set description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return get sessions
     */
    public List<JobSession> getSessions() {
        return sessions;
    }

    /**
     * @param sessions
     * set sessions
     */
    public void setSessions(List<JobSession> sessions) {
        this.sessions = sessions;
    }

    /**
     * @return get short name
     */
    public String getShort_name() {
        return short_name;
    }

    /**
     * @return get location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return get salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary
     * set salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * @return get required level
     */
    public int getRequired_level() {
        return required_level;
    }

    /**
     * @return get certificate
     */
    public List<AchievementCertificate> getRequired_achievements_certificate() {
        return required_achievements_certificate;
    }

    /**
     * @return get special achievements
     */
    public List<AchievementSpecial> getRequired_achievements_special() {
        return required_achievements_special;
    }

    /**
     * @return get categorized achievements
     */
    public List<AchievementCategorized> getRequired_achievements_categorized() {
        return required_achievements_categorized;
    }

    /**
     * @return get - gain special achievements
     */
    public List<AchievementSpecial> getGain_achievements_special() {
        return gain_achievements_special;
    }

    /**
     * @param tripSession
     * add session
     */
    public void addSession(JobSession tripSession) {
        this.sessions.add(tripSession);
    }

    /**
     * @return get job reviews
     */
    public List<JobResponse> getJobReviews() {
        if (jobResponses == null) jobResponses = new ArrayList<>();
        return jobResponses;
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
        this.author = author;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", possible_xp_reward=" + possible_xp_reward +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", required_level=" + required_level +
                ", category=" + category +
                ", required_achievements_certificate=" + required_achievements_certificate +
                ", required_achievements_special=" + required_achievements_special +
                ", required_achievements_categorized=" + required_achievements_categorized +
                ", gain_achievements_special=" + gain_achievements_special +
                ", sessions=" + sessions +
                '}';
    }
}
