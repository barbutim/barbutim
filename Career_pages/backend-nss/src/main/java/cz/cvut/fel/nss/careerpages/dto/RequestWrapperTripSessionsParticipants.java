package cz.cvut.fel.nss.careerpages.dto;

import java.util.List;

/**
 * Wrapper for request trip session participants
 */
public class RequestWrapperTripSessionsParticipants {

    private JobSessionDto session;
    private List<RequestWrapperEnrollmentGet> enrollments;

    /**
     * @param session
     * @param enrollments
     * constructor
     */
    public RequestWrapperTripSessionsParticipants(JobSessionDto session, List<RequestWrapperEnrollmentGet> enrollments) {
        this.session = session;
        this.enrollments = enrollments;
    }

    /**
     * @return get session
     */
    public JobSessionDto getSession() {
        return session;
    }

    /**
     * @param session
     * set session
     */
    public void setSession(JobSessionDto session) {
        this.session = session;
    }
}
