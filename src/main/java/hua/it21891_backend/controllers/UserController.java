package hua.it21891_backend.controllers;

import hua.it21891_backend.entities.User;
import hua.it21891_backend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/authenticated")
    @ResponseBody
    public List<User> getAllAuthenticatedUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        List<User> authenticatedUsers = new ArrayList<>();
        for (User user : users) {

            if (user.getRole().getRoleName().equals("ROLE_ADMIN")){
                continue;
            }

            if(user.isAuthenticated()){
                authenticatedUsers.add(user);
            }
        }

        return authenticatedUsers;
    }

    @GetMapping("/users/notAuthenticated")
    @ResponseBody
    public List<User> getAllNotAuthenticatedUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }


        List<User> notAuthenticatedUsers = new ArrayList<>();
        for (User user : users) {

            if (user.getRole().getRoleName().equals("ROLE_ADMIN")){
                continue;
            }

            if(!user.isAuthenticated()){
                notAuthenticatedUsers.add(user);
            }
        }
        return notAuthenticatedUsers;
    }

    @GetMapping("/userDetails")
    public User getUserDetails(Authentication authentication) {
        return this.userService.getUser(authentication);
    }

}
