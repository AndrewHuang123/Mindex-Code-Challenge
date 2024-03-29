package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compensations")
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping
    public Compensation createCompensation(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        return compensationService.createCompensation(compensation);
    }

    @GetMapping("/{employeeId}")
    public Compensation getCompensationsByEmployeeId(@PathVariable String employeeId) {
        LOG.debug("Received get request for employeeId [{}]", employeeId);

        return compensationService.getCompensationByEmployeeId(employeeId);
    }
}
