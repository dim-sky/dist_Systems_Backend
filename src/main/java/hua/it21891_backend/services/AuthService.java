package hua.it21891_backend.services;

import hua.it21891_backend.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private JwtService jwtService;

    public AuthService(PasswordEncoder passwordEncoder,UserService userService,JwtService jwtService){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Transactional
    public String register(User user) {
        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if(userService.isUsernameExist(user.getUserName())){
            throw new IllegalArgumentException("Το username χρησιμοποιείται ήδη: " + user.getUserName());
        }

        if(userService.isPasswordExist(user.getPassword())){
            throw new IllegalArgumentException("Ο κωδικός χρησιμοποιείται ήδη: " + user.getPassword());
        }

        if (userService.isEmailExist(user.getEmail())) {
            throw new IllegalArgumentException("Το email χρησιμοποιείται ήδη: " + user.getEmail());
        }


        userService.saveUser(user);
        return jwtService.generateToken(user.getUserName());
    }

    @Transactional
    public String login(String username, String password) {
        UserDetails user = userService.loadUserByUsername(username);

//        String passwordFromRequest = password;
//        String passwordFromDatabase = user.getPassword();
//        System.out.println(passwordFromRequest);
//        System.out.println(passwordFromDatabase);
//        System.out.println(passwordEncoder.matches(passwordFromRequest, passwordFromDatabase));
        // Check if password is correct
        if(!user.getUsername().equals(username)){
            throw new RuntimeException("Το username είναι λάθος");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("O κωδικός είναι λάθος");
        }

//        if (userService.isEmailExist(user.getEmail())) {
//            throw new IllegalArgumentException("Το email χρησιμοποιείται: " + user.getEmail());
//        }

        // Generate JWT token for the user after successful login
        return jwtService.generateToken(username);
    }
}
