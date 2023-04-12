package com.portal.centro.API.controller;

import com.portal.centro.API.generic.crud.GenericController;
import com.portal.centro.API.model.Partner;
import com.portal.centro.API.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/partners")
public class PartnerController extends GenericController<Partner, Long> {

    @Autowired
    public PartnerController(PartnerService partnerService) {
        super(partnerService);
    }
}
