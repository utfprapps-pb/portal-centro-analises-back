package com.portal.centro.API.generic.crud;

import com.portal.centro.API.exceptions.NotFoundException;
import com.portal.centro.API.model.ObjectReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<T, ID> {

    public final GenericRepository<T, ID> genericRepository;

    public GenericService(GenericRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
    }

    public T save(T requestBody) throws Exception {
        return genericRepository.save(requestBody);
    }

    public ObjectReturn deleteById(ID id) {
        genericRepository.deleteById(id);
        return new ObjectReturn("Registro deletado com sucesso.");
    }

    public List<T> getAll() {
        return genericRepository.findAll();
    }

    public Page<T> page(PageRequest pageRequest) {
        return genericRepository.findAll(pageRequest);
    }

    public Page<T> search(Specification specification, PageRequest pageRequest) {
        return genericRepository.findAll(specification, pageRequest);
    }

    public T findOneById(ID id) {
        Optional<T> optional = genericRepository.findById(id);
        if (optional.isPresent())
            return optional.get();

        throw new NotFoundException("Nenhum registro encontrado.");
    }

}
