package hua.it21891_backend.repositories;

import hua.it21891_backend.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    public UserRole findByRoleName(String roleName);
//    public List<UserRole> findAllByRoleId();
}
