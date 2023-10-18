package com.vn.hcmus.qlqtpm.backendvnexpress.controller;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.DashboardDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.response.APIResponseSuccess;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.DashboardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {
    public static final Logger logger = LogManager.getLogger(DashboardController.class);


    @Autowired
    private DashboardService dashboardService;

    @GetMapping(value = "")
    public ResponseEntity<APIResponseSuccess<List<DashboardDTO>>> getDashboard() {
        return ResponseEntity.ok(new APIResponseSuccess<>(dashboardService.getDashboard()));
    }
}
