package hua.it21891_backend.controllers;

import hua.it21891_backend.dtos.request.LoginRequest;
import hua.it21891_backend.entities.User;
import hua.it21891_backend.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private AuthService authService;  // You'll need to create this service

    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        String token = authService.register(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.userName(), loginRequest.password());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
//        String jwt = token.replace("Bearer ", "");
//
//        return ResponseEntity.ok("Logged out successfully.");
//    }
}
