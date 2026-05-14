package org.example.neonarkintaketracker.controller;

import org.example.neonarkintaketracker.dto.UserAdminResponse;
import org.example.neonarkintaketracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserAdminResponse>> listAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsersForGovernance());
    }
}