package cz.cvut.fel.nss.careerpages.service;

import org.springframework.stereotype.Service;
import java.util.Random;

/**
 * service for password
 */
@Service
public class PasswordService {

    private Random random;

    /**
     * constructor
     */
    public PasswordService() {
        random = new Random();
    }
}
