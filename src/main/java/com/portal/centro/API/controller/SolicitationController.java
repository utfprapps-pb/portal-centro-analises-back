package com.portal.centro.API.controller;

import com.portal.centro.API.dto.SolicitationRequestDto;
import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.generic.crud.GenericService;
import com.portal.centro.API.model.Solicitation;
import com.portal.centro.API.service.SolicitationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitar")
public class SolicitationController extends GenericController<Solicitation, Long> {

    public SolicitationController(SolicitationService solicitationService) {
        super(solicitationService);
    }

}
