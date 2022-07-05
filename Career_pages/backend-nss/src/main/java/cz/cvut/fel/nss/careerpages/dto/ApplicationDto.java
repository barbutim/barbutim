package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.ApplicationState;

import javax.persistence.Basic;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.util.List;

/**
 * dto for applications on job offers
 */
public class ApplicationDto {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @PastOrPresent
    private LocalDateTime enrollDate;

    @Basic(optional = false)
    private boolean deposit_was_paid;

    @Basic(optional = false)
    private int actual_xp_reward;

    @Enumerated(EnumType.STRING)
    private ApplicationState state;

    private List<AchievementSpecialDto> recieved_achievements_special;
    private Long jobJournalId;
    private WorkOfferDto trip;
    private JobSessionDto tripSession;
    private JobResponseDto jobReview;

    /**
     * @param id
     * @param enrollDate
     * @param deposit_was_paid
     * @param actual_xp_reward
     * @param state
     * @param recieved_achievements_special
     * @param jobJournalId
     * @param trip
     * @param tripSession
     * @param jobReview
     * constructor
     */
    public ApplicationDto(@NotNull(message = "Id cannot be blank") Long id, @PastOrPresent LocalDateTime enrollDate, boolean deposit_was_paid, int actual_xp_reward, ApplicationState state, List<AchievementSpecialDto> recieved_achievements_special,
                          Long jobJournalId, WorkOfferDto trip, JobSessionDto tripSession, JobResponseDto jobReview) {
        this.id = id;
        this.enrollDate = enrollDate;
        this.deposit_was_paid = deposit_was_paid;
        this.actual_xp_reward = actual_xp_reward;
        this.state = state;
        this.recieved_achievements_special = recieved_achievements_special;
        this.jobJournalId = jobJournalId;
        this.trip = trip;
        this.tripSession = tripSession;
        this.jobReview = jobReview;
    }

    /**
     * constructor
     */
    public ApplicationDto() {

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

    /**
     * @return get enroll date
     */
    public LocalDateTime getEnrollDate() {
        return enrollDate;
    }

    /**
     * @return was paid
     */
    public boolean isDeposit_was_paid() {
        return deposit_was_paid;
    }

    /**
     * @return xp
     */
    public int getActual_xp_reward() {
        return actual_xp_reward;
    }

    /**
     * @return state
     */
    public ApplicationState getState() {
        return state;
    }

    /**
     * @return get achievements
     */
    public List<AchievementSpecialDto> getRecieved_achievements_special() {
        return recieved_achievements_special;
    }

    /**
     * @return get offers
     */
    public WorkOfferDto getTrip() {
        return trip;
    }

    /**
     * @param trip
     * set offers
     */
    public void setTrip(WorkOfferDto trip) {
        this.trip = trip;
    }

    /**
     * @return get offer sessions
     */
    public JobSessionDto getTripSession() {
        return tripSession;
    }

    /**
     * @param tripSession
     * set offer sessions
     */
    public void setTripSession(JobSessionDto tripSession) {
        this.tripSession = tripSession;
    }
}
