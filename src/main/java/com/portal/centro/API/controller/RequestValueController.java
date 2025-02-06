package com.portal.centro.API.controller;

import com.portal.centro.API.service.RequestValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/request-value")
@RequiredArgsConstructor
public class RequestValueController {

    private final RequestValueService requestValueService;

    // TODO
//    @PostMapping("/calculate")
//    public BigDecimal calculate(@RequestBody TechnicalReport technicalReport) {
//        return requestValueService.calculate(technicalReport);
//    }
}
