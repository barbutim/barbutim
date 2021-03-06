package cz.cvut.fel.nss.careerpages.dto;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

/**
 * Dto for work offers
 */
public class WorkOfferDto implements Serializable {

    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @Size(max = 255, min = 3, message = "Name has to be from 3 to 255 characters.")
    @NotNull(message = "Name has to be from 3 to 255 characters.")
    private String name;

    @Basic(optional = false)
    @Size(max = 100, min = 3, message = "Short name has to be from 3 to 100 characters.")
    @NotNull(message = "Short name has to be from 3 to 100 characters.")
    private String short_name;

    @Basic(optional = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 20, message = "Max 20")
    private int possible_xp_reward;

    @Basic(optional = false)
    @Size(max = 3000, min = 0, message = "Max 3000 characters.")
    private String description;

    @Basic(optional = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 5, message = "Max 5")
    private double rating;

    @Basic(optional = false)
    @Min(value = 0, message = "Min 0")
    @Max(value = 10000, message = "Max 10 000")
    private double salary;

    @Basic(optional = false)
    @Column(nullable = false)
    @Size(max = 200, min = 0, message = "Max 200 characters.")
    private String location;

    @Basic(optional = false)
    @Min(value = 0, message = "Min 0")
    private int required_level;

    private CategoryDto category;
    private Long authorId;
    private List<AchievementCertificateDto> required_achievements_certificate;
    private List<AchievementCategorizedDto> required_achievements_categorized;
    private List<AchievementSpecialDto> required_achievements_special;
    private List<AchievementSpecialDto> gain_achievements_special;
    private List<JobSessionDto> sessions;
    private List<JobResponseDto> jobResponseDtos;

    /**
     * @param id
     * @param name
     * @param short_name
     * @param possible_xp_reward
     * @param description
     * @param rating
     * @param salary
     * @param location
     * @param required_level
     * @param categoryDto
     * @param authorId
     * @param required_certificates
     * @param required_achievements_categorized
     * @param required_achievements_special
     * @param gain_achievements
     * @param sessions
     * @param jobResponseDtos
     * constructor
     */
    public WorkOfferDto(@NotNull(message = "Id cannot be blank") Long id, @Size(max = 255, min = 3, message = "Name has to be from 3 to 255 characters.") @NotNull(message = "Name has to be from 3 to 255 characters.") String name, @Size(max = 100, min = 3, message = "Short name has to be from 3 to 100 characters.") @NotNull(message = "Short name has to be from 3 to 100 characters.") String short_name,
                        @Min(value = 0, message = "Min 0") @Max(value = 20, message = "Max 20") int possible_xp_reward, @Size(max = 3000, min = 0, message = "Max 3000 characters.") String description, @Min(value = 0, message = "Min 0") @Max(value = 5, message = "Max 5") double rating,
                        @Min(value = 0, message = "Min 0") @Max(value = 10000, message = "Max 10 000") double salary, @Size(max = 200, min = 0, message = "Max 200 characters.") String location, @Min(value = 0, message = "Min 0") int required_level, CategoryDto categoryDto, Long authorId, List<AchievementCertificateDto> required_certificates,
                        List<AchievementCategorizedDto> required_achievements_categorized, List<AchievementSpecialDto> required_achievements_special,
                        List<AchievementSpecialDto> gain_achievements, List<JobSessionDto> sessions, List<JobResponseDto> jobResponseDtos) {
        this.id = id;
        this.name = name;
        this.short_name = short_name;
        this.possible_xp_reward = possible_xp_reward;
        this.description = description;
        this.rating = rating;
        this.salary = salary;
        this.location = location;
        this.required_level = required_level;
        this.category = categoryDto;
        this.required_achievements_certificate = required_certificates;
        this.required_achievements_categorized = required_achievements_categorized;
        this.required_achievements_special = required_achievements_special;
        this.gain_achievements_special = gain_achievements;
        this.sessions = sessions;
        this.jobResponseDtos = jobResponseDtos;
        this.authorId = authorId;
    }

    /**
     * constructor
     */
    public WorkOfferDto() {

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
     * @return get salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @return get category
     */
    public CategoryDto getCategory() {
        return category;
    }

    /**
     * @param category
     * set category
     */
    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    /**
     * @return get sessions
     */
    public List<JobSessionDto> getSessions() {
        return sessions;
    }

    /**
     * @param sessions
     * set sessions
     */
    public void setSessions(List<JobSessionDto> sessions) {
        this.sessions = sessions;
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
     * @return string
     */
    @Override
    public String toString() {
        return "WorkOfferDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", possible_xp_reward=" + possible_xp_reward +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", salary=" + salary +
                ", location='" + location + '\'' +
                ", required_level=" + required_level +
                ", author=" + authorId +
                ", required_achievements_certificate=" + required_achievements_certificate +
                ", required_achievements_categorized=" + required_achievements_categorized +
                ", required_achievements_special=" + required_achievements_special +
                ", gain_achievements_special=" + gain_achievements_special +
                ", sessionsDto=" + sessions +
                '}';
    }
}
