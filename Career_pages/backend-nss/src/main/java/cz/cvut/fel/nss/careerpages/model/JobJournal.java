package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Model for job journal
 */
@Entity
@Table(name = "TRAVELJOURNAL")
public class JobJournal extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private int xp_count = 0;

    @Type(type = "json")
    @Column(nullable = false)
    private HashMap<Long, Integer> trip_counter;

    @JsonIgnore
    @OneToOne(mappedBy = "travel_journal")
    private User user;

    @ManyToMany
    private List<AchievementCertificate> certificates;

    @ManyToMany
    private List<AchievementCategorized> earnedAchievementsCategorized;

    @ManyToMany
    private List<AchievementSpecial> earnedAchievementsSpecial;

    @OneToMany(mappedBy = "jobJournal")
    private List<Application> applications;

    /**
     * constructor
     */
    public JobJournal() {
        this.trip_counter = new HashMap<Long,Integer>();
        this.applications = new ArrayList<Application>();
        this.earnedAchievementsCategorized = new ArrayList<AchievementCategorized>();
        this.earnedAchievementsSpecial = new ArrayList<AchievementSpecial>();
        this.certificates = new ArrayList<AchievementCertificate>();
    }

    /**
     * @param user
     * constructor
     */
    public JobJournal(User user) {
        this.user = user;
        this.trip_counter = new HashMap<Long,Integer>();
        this.applications = new ArrayList<Application>();
        this.earnedAchievementsCategorized = new ArrayList<AchievementCategorized>();
        this.earnedAchievementsSpecial = new ArrayList<AchievementSpecial>();
        this.certificates = new ArrayList<AchievementCertificate>();
    }

    /**
     * @return get xp
     */
    public int getXp_count() {
        return xp_count;
    }

    /**
     * @return get trip counter
     */
    public HashMap<Long, Integer> getTrip_counter() {
        return trip_counter;
    }

    /**
     * @return get user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param xp_count
     * set xp
     */
    public void setXp_count(int xp_count) {
        this.xp_count = xp_count;
    }

    /**
     * @param trip_counter
     * set trip counter
     */
    public void setTrip_counter(HashMap<Long, Integer> trip_counter) {
        this.trip_counter = trip_counter;
    }

    /**
     * @param user
     * set user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return get certificates
     */
    public List<AchievementCertificate> getCertificates() {
        return certificates;
    }

    /**
     * @param achievementCertificate
     * add certificate
     */
    public void addCertificate(AchievementCertificate achievementCertificate) {
        this.certificates.add(achievementCertificate);
    }

    /**
     * @return get enrollments
     */
    public List<Application> getEnrollments() {
        return applications;
    }

    /**
     * @param applications
     * set enrollment
     */
    public void setEnrollments(List<Application> applications) {
        this.applications = applications;
    }

    /**
     * @param application
     * add enrollment
     */
    public void addEnrollment(Application application){
        if (applications == null) applications = new ArrayList<Application>();
        applications.add(application);
    }

    /**
     * @return get earned achievements by category
     */
    public List<AchievementCategorized> getEarnedAchievementsCategorized() {
        return earnedAchievementsCategorized;
    }

    /**
     * @param achievementCategorized
     * add earned achievements by category
     */
    public void addEarnedAchievementCategorized(AchievementCategorized achievementCategorized) {
        if(earnedAchievementsCategorized.contains(achievementCategorized)) {
            return;
        }
        this.earnedAchievementsCategorized.add(achievementCategorized);
    }

    /**
     * @return get earned achievements special
     */
    public List<AchievementSpecial> getEarnedAchievementsSpecial() {
        return earnedAchievementsSpecial;
    }

    /**
     * @param achievementSpecial
     * add earned achievements special
     */
    public void addEarnedAchievementSpecial(AchievementSpecial achievementSpecial) {
        if(earnedAchievementsSpecial.contains(achievementSpecial)) {
            return;
        }
        this.earnedAchievementsSpecial.add(achievementSpecial);
    }

    /**
     * Adds trip to travel journal
     * If travel journal already contains the category, adds one more.
     * If doesn't, adds a new category counted with one trip in there.
     */
    public void addTrip(Long category){
        int actualValue = findAndGetCategoryValueIfExists(category);
        if(actualValue != -1) {
            this.trip_counter.put(category, trip_counter.get(category).intValue() + 1);
            System.out.println("||||ACTUAL COUNTER OF TRIPS :" + findAndGetCategoryValueIfExists(category));
        }
        else{
            this.trip_counter.put(category, 1);
            System.out.println("||||Adding COUNTER OF TRIPS :" + findAndGetCategoryValueIfExists(category));
        }
    }


    /**
     * @param category
     * @return if it has to be private we can copy it, but it would be great to have this accesible in services
     */
    public int findAndGetCategoryValueIfExists(Long category){
        for (Long key: this.trip_counter.keySet()) {
            if(key.equals(category)){
                return this.trip_counter.get(key).intValue();
            }
        }
        return -1;
    }

    /**
     * @param xp
     * adds xp
     */
    public void addsXp(int xp){
        this.xp_count += xp;
    }
}
