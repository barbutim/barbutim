package cz.cvut.fel.nss.careerpages.rest;

import cz.cvut.fel.nss.careerpages.dto.AbstractUserDto;
import cz.cvut.fel.nss.careerpages.exception.AlreadyLoginException;
import cz.cvut.fel.nss.careerpages.security.SecurityConstants;
import cz.cvut.fel.nss.careerpages.service.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

/**
 * Rest login controller
 */
@RestController
@CrossOrigin(origins = SecurityConstants.FRONTEND, allowCredentials="true", allowedHeaders = "*")
public class LoginController {

    private LoginService service;

    /**
     * @param service
     * constructor
     */
    @Autowired
    public LoginController(LoginService service) {
         this.service = service;
    }

    /**
     * method login user to app
     * @param request
     * @return response of current AbstractUserDto
     * @throws AlreadyLoginException
     */
    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AbstractUserDto> loginUser(@RequestBody HashMap<String,String> request) throws AlreadyLoginException {

        return ResponseEntity.status(HttpStatus.OK).body(service.login(request.get("email"),request.get("password")));
    }
}
