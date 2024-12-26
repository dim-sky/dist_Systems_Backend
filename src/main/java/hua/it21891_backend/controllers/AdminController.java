package hua.it21891_backend.controllers;

import hua.it21891_backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")  // This makes all endpoints in this controller admin-only
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/{userId}/approve")
    public ResponseEntity<String> approveUser(@PathVariable int userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok("User approved successfully");
    }
}