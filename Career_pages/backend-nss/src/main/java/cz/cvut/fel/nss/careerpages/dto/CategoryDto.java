package cz.cvut.fel.nss.careerpages.dto;

import javax.persistence.Basic;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * dto for work categories
 */
public class CategoryDto implements Serializable
{
    @NotNull(message = "Id cannot be blank")
    private Long id;

    @Basic(optional = false)
    @NotNull(message = "Name of category cannot be blank")
    private String name;

    /**
     * @param id
     * @param name
     * constructor
     */
    public CategoryDto(@NotNull(message = "Id cannot be blank") Long id,
                       @NotNull(message = "Name of category cannot be blank") String name) {

        this.id = id;
        this.name = name;
    }

    /**
     * constructor
     */
    public CategoryDto() {

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
}
