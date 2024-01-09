package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/reportingStructure")
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{employeeId}")
    public ReportingStructure getReportingStructure(@PathVariable String employeeId) {
        LOG.debug("Received read request for employeeId [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);

        if (employee == null) {
            return null;
        }

        int numberOfReports = calculateNumberOfReports(employee);
        return new ReportingStructure(employee, numberOfReports);
    }

    private int calculateNumberOfReports(Employee employee) {
        // Null Checks
        if (employee == null || employee.getDirectReports() == null) {
            return 0;
        }

        int directReportsCount = employee.getDirectReports().size();

        // Calculate the total number of reports recursively
        return directReportsCount +
                employee.getDirectReports().stream()
                        .map(this::calculateNumberOfReports)
                        .reduce(0, Integer::sum);
    }
}