package hua.it21891_backend.services;

import hua.it21891_backend.entities.UserRole;
import hua.it21891_backend.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService {

    private UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public String getRoleNameFromId( int id ) {
        Optional<UserRole> temp = userRoleRepository.findById( id );
        return temp.isPresent() ? temp.get().getRoleName() : null;
    }




   @PostConstruct
   @Transactional
   public void init() {
       UserRole volunteer = new UserRole(1,"ROLE_VOLUNTEER");
       UserRole organization = new UserRole(2,"ROLE_ORGANIZATION");
       UserRole admin = new UserRole(3,"ROLE_ADMIN");

       userRoleRepository.save( volunteer );
       userRoleRepository.save( organization );
       userRoleRepository.save( admin );
   }
}
