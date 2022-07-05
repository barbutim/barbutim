package cz.cvut.fel.nss.careerpages.model;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model for application
 */
@Entity
@Table(name = "ENROLLMENT")
public class Application extends AbstractEntity {
    @Basic(optional = false)
    @Column(nullable = false)
    @PastOrPresent
    private LocalDateTime enrollDate;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean deposit_was_paid;

    @Basic(optional = false)
    @Column(nullable = false)
    private int actual_xp_reward;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicationState state;

    @ManyToMany
    @JoinTable(name = "recieved_achievement_special_trip",
            joinColumns = @JoinColumn(name = "enrollment_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_special_id"))
    private List<AchievementSpecial> recieved_achievements_special;

    @ManyToOne( optional = false)
    @JoinColumn(name = "jobJournal_id", nullable = false)
    private JobJournal jobJournal;

    @ManyToOne( optional = false)
    @JoinColumn(name = "trip_id", nullable = false)
    private Offer trip;

    @ManyToOne( optional = false)
    @JoinColumn(name = "tripSession_id", nullable = false)
    private JobSession tripSession;

    @OneToOne(mappedBy = "enrollment")
    private JobResponse jobResponse;

    /**
     * @return get enroll date
     */
    public LocalDateTime getEnrollDate() {
        return enrollDate;
    }

    /**
     * @return was paid deposit
     */
    public boolean isDeposit_was_paid() {
        return deposit_was_paid;
    }

    /**
     * @return xp reward
     */
    public int getActual_xp_reward() {
        return actual_xp_reward;
    }

    /**
     * @return get job journal
     */
    public JobJournal getJobJournal() {
        return jobJournal;
    }

    /**
     * @return get trip
     */
    public Offer getTrip() {
        return trip;
    }

    /**
     * @return get trip session
     */
    public JobSession getTripSession() {
        return tripSession;
    }

    /**
     * @param enrollDate
     * set enroll date
     */
    public void setEnrollDate(LocalDateTime enrollDate) {
        this.enrollDate = enrollDate;
    }

    /**
     * @param deposit_was_paid
     * set deposit if paid
     */
    public void setDeposit_was_paid(boolean deposit_was_paid) {
        this.deposit_was_paid = deposit_was_paid;
    }

    /**
     * @param actual_xp_reward
     * set xp
     */
    public void setActual_xp_reward(int actual_xp_reward) {
        this.actual_xp_reward = actual_xp_reward;
    }

    /**
     * @param jobJournal
     * set job journal
     */
    public void setJobJournal(JobJournal jobJournal) {
        this.jobJournal = jobJournal;
    }

    /**
     * @param trip
     * set trip
     */
    public void setTrip(Offer trip) {
        this.trip = trip;
    }

    /**
     * @param tripSession
     * set trip session
     */
    public void setTripSession(JobSession tripSession) {
        this.tripSession = tripSession;
    }

    /**
     * @return get state
     */
    public ApplicationState getState() {
        return state;
    }

    /**
     * @param state
     * set state
     */
    public void setState(ApplicationState state) {
        this.state = state;
    }

    /**
     * @return is finished
     */
    public boolean isFinished(){
        return this.getState() == ApplicationState.FINISHED;
    }

    /**
     * @return get received achievements
     */
    public List<AchievementSpecial> getRecieved_achievements() {
        return recieved_achievements_special;
    }

    /**
     * @return get job review
     */
    public JobResponse getJobReview() {
        return jobResponse;
    }

    /**
     * @return has job review
     */
    public boolean hasJobReview() {
        return jobResponse != null;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return "Application{" +
                "enrollDate=" + enrollDate +
                ", deposit_was_paid=" + deposit_was_paid +
                ", actual_xp_reward=" + actual_xp_reward +
                ", state=" + state +
                ", recieved_achievements_special=" + recieved_achievements_special +
                ", jobJournal=" + jobJournal +
                ", trip=" + trip +
                ", tripSession=" + tripSession +
                "} " + super.toString();
    }

    public void setRecieved_achievements_special(List<AchievementSpecial> recieved_achievements_special) {
        this.recieved_achievements_special = recieved_achievements_special;
    }
}
