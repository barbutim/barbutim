package cz.cvut.fel.nss.careerpages.service;

import cz.cvut.fel.nss.careerpages.dao.AddressDao;
import cz.cvut.fel.nss.careerpages.dao.ApplicantDao;
import cz.cvut.fel.nss.careerpages.dto.AddressDto;
import cz.cvut.fel.nss.careerpages.dto.ApplicantDto;
import cz.cvut.fel.nss.careerpages.exception.NotFoundException;
import cz.cvut.fel.nss.careerpages.model.AbstractUser;
import cz.cvut.fel.nss.careerpages.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

/**
 * Service for translate back
 */
@Service
public class TranslateBackService {

    private final ApplicantDao applicantDao;
    private final AddressDao addressDao;

    /**
     * @param applicantDao
     * @param addressDao
     * constructor
     */
    @Autowired
    public TranslateBackService(ApplicantDao applicantDao, AddressDao addressDao) {
        this.applicantDao = applicantDao;
        this.addressDao = addressDao;
    }

    /**
     * Translate object applicantDto to User
     * @param applicantDto
     * @return AbstractUser
     * @throws NotFoundException
     */
    @Transactional
    public AbstractUser translateUser(ApplicantDto applicantDto) throws NotFoundException {
        Objects.requireNonNull(applicantDto);
        AbstractUser user = applicantDao.find(applicantDto.getId());
        if (user == null) throw new NotFoundException();

        user.setEmail(applicantDto.getEmail());
        user.setFirstName(applicantDto.getFirstName());
        user.setLastName(applicantDto.getLastName());
        user.setAddress(translateAddress(applicantDto.getAddress()));
        return user;
    }

    /**
     * Translate object addressDto to Address
     * @param addressDto
     * @return Address
     */
    @Transactional
    public Address translateAddress(AddressDto addressDto) {
        Objects.requireNonNull(addressDto);
        Address address = addressDao.find(addressDto.getId());

        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setStreet(addressDto.getStreet());
        address.setZipCode(addressDto.getZipCode());
        return address;
    }
}
