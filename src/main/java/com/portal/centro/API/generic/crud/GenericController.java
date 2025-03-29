package com.portal.centro.API.generic.crud;

import com.google.common.base.Joiner;
import com.portal.centro.API.enums.SearchOperation;
import com.portal.centro.API.generic.specification.GenericSpecification;
import com.portal.centro.API.generic.specification.GenericSpecificationsBuilder;
import com.portal.centro.API.model.ObjectReturn;
import com.portal.centro.API.model.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.portal.centro.API.enums.Type.ROLE_ADMIN;

public abstract class GenericController<T extends GenericModel, ID extends Serializable> {

    private final GenericService<T, ID> genericService;

    public GenericController(GenericService<T, ID> genericService) {
        this.genericService = genericService;
    }

    @PostMapping("/save")
    public ResponseEntity<T> save(@RequestBody @Valid T requestBody) throws Exception {
        return ResponseEntity.ok(genericService.save(requestBody));
    }

    @PostMapping("/update")
    public ResponseEntity<T> update(@Valid @RequestBody T requestBody) throws Exception {
        return ResponseEntity.ok(genericService.update(requestBody));
    }

    @PostMapping("/{id}")
    public ResponseEntity<T> findOneById(@PathVariable ID id) {
        return ResponseEntity.ok(genericService.findOneById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ID id) throws Exception {
        return ResponseEntity.ok(genericService.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll() throws Exception {
        return ResponseEntity.ok(genericService.getAll());
    }

}
