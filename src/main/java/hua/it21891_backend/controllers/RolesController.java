package hua.it21891_backend.controllers;


import hua.it21891_backend.services.UserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    UserRoleService userRoleService;

    public RolesController( UserRoleService userRoleService ) {
        this.userRoleService = userRoleService;
    }

}
