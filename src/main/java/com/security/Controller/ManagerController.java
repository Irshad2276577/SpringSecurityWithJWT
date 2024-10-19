package com.security.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
// Controller for handling admin-related requests
public class ManagerController {

    // Endpoint to verify access for admin users
    @GetMapping
    public ResponseEntity<String> verifyMethod() {
        // Return a response indicating successful access
        return ResponseEntity.ok("It is a beautiful day!!!");
    }
}
