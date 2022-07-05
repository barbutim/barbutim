package cz.cvut.fel.nss.careerpages.dto;

import javax.validation.constraints.NotNull;

/**
 * Wrapper for request enrollment
 */
public class RequestWrapperEnrollmentGet {

    @NotNull(message = "Application cannot be blank")
    private ApplicationDto applicationDto;

    @NotNull(message = "User cannot be blank")
    private ApplicantDto owner;

    /**
     * @param applicationDto
     * @param applicantDto
     * constructor
     */
    public RequestWrapperEnrollmentGet(@NotNull(message = "Application cannot be blank") ApplicationDto applicationDto,
                                       @NotNull(message = "User cannot be blank") ApplicantDto applicantDto) {
        this.applicationDto = applicationDto;
        this.owner = applicantDto;
    }

    /**
     * constructor
     */
    public RequestWrapperEnrollmentGet() {

    }

    /**
     * @param applicationDto
     * set Enrollment
     */
    public void setEnrollmentDto(ApplicationDto applicationDto) {
        this.applicationDto = applicationDto;
    }

    /**
     * @param owner
     * set owner
     */
    public void setOwner(ApplicantDto owner) {

        this.owner = owner;
    }
}
