package cz.cvut.kbss.ear.rest;

import cz.cvut.kbss.ear.exception.NotFoundException;
import cz.cvut.kbss.ear.exception.ValidationException;
import cz.cvut.kbss.ear.model.Equipment;
import cz.cvut.kbss.ear.rest.util.RestUtils;
import cz.cvut.kbss.ear.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/equipment")
public class EquipmentController {

    private final static Logger LOG = LoggerFactory.getLogger(EquipmentController.class);

    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping
    public List<Equipment> getEquipment() {
        return equipmentService.findAll();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Equipment getEquipmentById(@PathVariable int id) {
        final Equipment equipment = equipmentService.findById(id);
        if(equipment == null) {
            throw NotFoundException.create("Equipment", id);
        }
        return equipment;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createEquipment(@RequestBody Equipment equipment) {
        equipmentService.persist(equipment);
        LOG.debug("Equipment with id {} has been created.", equipment.getId());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", equipment.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        final Equipment defaultEquipment = equipmentService.findById(id);
        if(!defaultEquipment.getId().equals(equipment.getId())) {
            throw new ValidationException("ID does not match with the requested one.");
        }
        equipmentService.update(equipment);
        LOG.debug("Equipment with id {} has been updated.", equipment.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEquipment(@PathVariable int id) {
        final Equipment equipment = getEquipmentById(id);
        if(equipment == null) {
            return;
        }
        equipmentService.remove(equipment);
        LOG.debug("Equipment with id {} has been deleted.", equipment.getId());
    }
}