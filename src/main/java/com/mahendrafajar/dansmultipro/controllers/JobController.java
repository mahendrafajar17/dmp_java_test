package com.mahendrafajar.dansmultipro.controllers;

import com.mahendrafajar.dansmultipro.services.DMPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    DMPService dmpService;

    @GetMapping
    public ResponseEntity<Object> getJobs() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return ResponseEntity.ok().body(dmpService.getJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getJobDetails(@PathVariable(value = "id") String id) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return ResponseEntity.ok().body(dmpService.getJobDetails(id));
    }
}
