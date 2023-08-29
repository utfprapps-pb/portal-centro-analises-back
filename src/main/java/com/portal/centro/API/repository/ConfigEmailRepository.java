package com.portal.centro.API.repository;

import com.portal.centro.API.generic.crud.GenericRepository;
import com.portal.centro.API.model.ConfigEmail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigEmailRepository extends GenericRepository<ConfigEmail, Long> {
}
