package cz.cvut.fel.nss.careerpages.dto;

import cz.cvut.fel.nss.careerpages.model.UserReview;
import javax.validation.constraints.NotNull;

/**
 * Whapper for request enrollmment
 */
public class RequestWrapperEnrollment {

    @NotNull(message = "User review cannot be blank")
    private UserReview userReview;

    @NotNull(message = "Session id cannot be blank")
    private Long tripSessionId;

    @NotNull(message = "Application cannot be blank")
    private ApplicationDto applicationDto;

    /**
     * @param userReview
     * @param tripSessionId
     * @param applicationDto
     * constructor
     */
    public RequestWrapperEnrollment(@NotNull(message = "User review cannot be blank") UserReview userReview, @NotNull(message = "Session id cannot be blank") Long tripSessionId, @NotNull(message = "Application cannot be blank") ApplicationDto applicationDto) {
        this.userReview = userReview;
        this.tripSessionId = tripSessionId;
        this.applicationDto = applicationDto;
    }

    /**
     * constructor
     */
    public RequestWrapperEnrollment() {

    }

    /**
     * @return get user review
     */
    public UserReview getUserReview() {
        return userReview;
    }

    /**
     * @return get trip session id
     */
    public Long getTripSessionId() {
        return tripSessionId;
    }

    /**
     * @return get enrollment
     */
    public ApplicationDto getEnrollmentDto() {
        return applicationDto;
    }
}
