package com.portal.centro.API.generic.crud;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

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
    public ResponseEntity<T> findOneById(@PathVariable ID id) throws Exception {
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
