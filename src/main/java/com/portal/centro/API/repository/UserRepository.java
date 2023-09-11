package com.portal.centro.API.repository;

import com.portal.centro.API.enums.StatusInactiveActive;
import com.portal.centro.API.enums.Type;
import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericRepository<User, Long> {

    User findByEmail(String email);

    List<User> findAllByRole(Type role);
    Page<User> findAllByRole(Type role, PageRequest pageRequest);

    List<User> findAllByStatus(StatusInactiveActive status);

    User findUserById(Long id);
}
