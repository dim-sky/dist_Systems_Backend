package hua.it21891_backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserRole {

    @Id
    private int roleId;

    @Column( nullable=false )
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public UserRole() {}

    public UserRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
