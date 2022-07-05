package cz.cvut.fel.nss.careerpages.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Abstract entity model
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = true)
    @Column(nullable = true)
    private LocalDate deleted_at;

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
     * soft delete
     */
    public void softDelete(){
        deleted_at = LocalDate.now();
    }

    /**
     * @return is not deleted
     */
    @JsonIgnore
    public boolean isNotDeleted(){
        return deleted_at == null;
    }

}
