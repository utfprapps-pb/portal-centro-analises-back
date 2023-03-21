package com.portal.centro.API.user;

import com.portal.centro.API.generic.crud.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService extends GenericService<User, Long> {

    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        super(userRepository);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User save(User requestBody) throws Exception {
        requestBody.setPassword( passwordEncoder.encode(requestBody.getPassword()));
        requestBody.setInclusionDate(LocalDateTime.now());
        return super.save(requestBody);
    }
}
