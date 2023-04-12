package com.portal.centro.API.service;

import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.User;
import com.portal.centro.API.repository.UserRepository;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User, Long> {

    BCryptPasswordEncoder passwordEncoder;
    private final UtilsService utilsService;

    @Autowired
    public UserService(UserRepository userRepository, UtilsService utilsService) {
        super(userRepository);
        this.utilsService = utilsService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User requestBody) throws Exception {
        requestBody.setPassword( passwordEncoder.encode(requestBody.getPassword()));
        Type role = utilsService.getRoleType(requestBody.getEmail());
        requestBody.setRole(role);
        return super.save(requestBody);
    }
}
