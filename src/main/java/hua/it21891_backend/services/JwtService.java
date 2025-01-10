package hua.it21891_backend.services;

import hua.it21891_backend.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secretKey = "yourStrongSecretKey12345"; // hardcoded secret key
    private long expirationTime = 600000; // expiration time of 10 minutes in milliseconds

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    // Generate JWT token based on Account
    public String generateToken(String username) {
        User user = userService.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRole().getRoleName(); // Assuming 'getRole' returns the user's role
        boolean isApproved = user.isAuthenticated();

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
//                .claim("customAuthentication", isApproved)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Check if the JWT token is expired
    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }


}
