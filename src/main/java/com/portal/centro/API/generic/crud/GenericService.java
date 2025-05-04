package com.portal.centro.API.generic.crud;

import com.portal.centro.API.exceptions.NotFoundException;
import com.portal.centro.API.model.ObjectReturn;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<T extends GenericModel, ID> {

    public final GenericRepository<T, ID> genericRepository;

    public GenericService(GenericRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Transactional
    public T save(T requestBody) throws Exception {
        if (ObjectUtils.isNotEmpty(requestBody.getId())) {
            return this.update(requestBody);
        } else {
            genericRepository.saveAndFlush(requestBody);
            return findOneById((ID) requestBody.getId());
        }
    }

    @Transactional
    public T update(T requestBody) throws Exception {
        genericRepository.saveAndFlush(requestBody);
        return findOneById((ID) requestBody.getId());
    }

    @Transactional
    public ObjectReturn deleteById(ID id) throws Exception {
        genericRepository.deleteById(id);
        return new ObjectReturn("Registro deletado com sucesso.");
    }

    public List<T> getAll() {
        return genericRepository.findAll();
    }


    public T findOneById(ID id) throws Exception {
        Optional<T> optional = genericRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundException("Nenhum registro encontrado.");
    }

}
