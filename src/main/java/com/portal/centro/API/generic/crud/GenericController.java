package com.portal.centro.API.generic.crud;

import com.google.common.base.Joiner;
import com.portal.centro.API.enums.SearchOperation;
import com.portal.centro.API.generic.specification.GenericSpecification;
import com.portal.centro.API.generic.specification.GenericSpecificationsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class GenericController<T, ID extends Serializable> {

    private final GenericService<T, ID> genericService;

    public GenericController(GenericService<T, ID> genericService) {
        this.genericService = genericService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid T requestBody) throws Exception {
        return saveOrUpdate(requestBody);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody @Valid T requestBody) throws Exception {
        return saveOrUpdate(requestBody);
    }

    private ResponseEntity saveOrUpdate(@RequestBody T requestBody) throws Exception {
        return ResponseEntity.ok(genericService.save(requestBody));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable ID id) throws Exception {
        return ResponseEntity.ok(genericService.deleteById(id));
    }

    @GetMapping
    public ResponseEntity getAll() throws Exception {
        return ResponseEntity.ok(genericService.getAll());
    }

    @GetMapping("page")
    public Page<T> search(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "sort",required = false) Boolean asc
    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return genericService.page(pageRequest);
    }

    @GetMapping("search")
    public Page<T> search(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "order",required = false) String order,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @RequestParam(value = "search", required = false) String search
    ) throws Exception {
        Specification<T> spec = resolveSpecification(search);
        PageRequest pageRequest = PageRequest.of(page, size);
        if (order != null && asc != null) {
            pageRequest = PageRequest.of(page, size,
                    asc ? Sort.Direction.ASC : Sort.Direction.DESC, order);
        }
        return genericService.search(spec, pageRequest);
    }

    protected Specification<T> resolveSpecification(String searchParameters) {
        GenericSpecificationsBuilder<T> specBuilder = new GenericSpecificationsBuilder<>();
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+?)(" + Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters + ",");
        while (matcher.find()) {
            specBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }
        return specBuilder.build(GenericSpecification<T>::new);
    }

    @PostMapping("{id}")
    public ResponseEntity findOneById(@PathVariable ID id) {
        return ResponseEntity.ok(genericService.findOneById(id));
    }

}
