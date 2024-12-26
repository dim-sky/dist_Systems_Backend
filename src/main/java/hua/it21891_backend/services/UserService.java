package hua.it21891_backend.services;


import hua.it21891_backend.entities.User;
import hua.it21891_backend.entities.UserRole;
import hua.it21891_backend.repositories.UserRepository;
import hua.it21891_backend.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,UserRoleRepository userRoleRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> findUserByUsername(String username ) {
        return userRepository.findByUserName(username);
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(User user) {}

    @Transactional
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUserName(username);
    }

    @Transactional
    public boolean isPasswordExist(String password) {
        return userRepository.existsByPassword(password);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()))
        );
    }

    @Transactional
    public void approveUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found with id: " + userId));

        user.setAuthenticated(true);
        userRepository.save(user);
    }

    @Transactional
    public void createUser(String userName, String password, String email, String name, String roleName) {
        // Check if the role exists
        UserRole role = userRoleRepository.findByRoleName(roleName);

        // Create and save the user
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Always encode passwords
        user.setName(name);
        user.setRole(role);
        user.setAuthenticated(false); // Default to unverified

        userRepository.save(user);
    }


    @Transactional
    public User getUser(Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ο χρήστης δεν βρέθηκε"));

        return user;

    }

//    @PostConstruct
//    @Transactional
//    public void postConstruct() {
//        createUser("User_1","password_1", "email_1","name_1","ROLE_VOLUNTEER");
//        createUser("User_2","password_2", "email_2","name_2","ROLE_ORGANIZATION");
//        createUser("User_3","password_3", "email_3","name_3","ROLE_ADMIN");
//
//    }


}
