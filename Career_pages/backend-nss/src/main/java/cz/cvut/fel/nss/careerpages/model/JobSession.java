package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for job session
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "JobSession.findByTrip", query = "SELECT t FROM JobSession t WHERE t.trip.short_name = :trip_short_name AND t.deleted_at is null")
})
@Table(name = "JOB_SESSION")
public class JobSession extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate from_date;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate to_date;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(value = 1, message = "Min 1")
    @Max(value = 500, message = "Max 500")
    private int capacity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Offer trip;

    @OneToMany(mappedBy = "tripSession")
    private List<Application> applications;

    /**
     * constructor
     */
    public JobSession() {
        this.applications = new ArrayList<>();
    }

    /**
     * @param trip
     * @param from_date
     * @param to_date
     * @param capacity
     * constructor
     */
    public JobSession(Offer trip, LocalDate from_date, LocalDate to_date, int capacity) {
        this.trip = trip;
        this.from_date = from_date;
        this.to_date = to_date;
        this.capacity = capacity;
        this.applications = new ArrayList<>();
    }

    /**
     * @return get from date
     */
    public LocalDate getFrom_date() {
        return from_date;
    }

    /**
     * @param from_date
     * set from date
     */
    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    /**
     * @return get to date
     */
    public LocalDate getTo_date() {
        return to_date;
    }

    /**
     * @param to_date
     * set to date
     */
    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }

    /**
     * @return get capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity
     * set capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return get trip
     */
    public Offer getTrip() {
        return trip;
    }

    /**
     * @param trip
     * set trip
     */
    public void setTrip(Offer trip) {
        this.trip = trip;
    }

    /**
     * @return get enrollments
     */
    @JsonIgnore
    public List<Application> getEnrollments() {
        return applications;
    }
}
