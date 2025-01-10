package hua.it21891_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column( unique = true, nullable = false )
    private String userName;

    @Column( unique = true, nullable = false )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column( unique = true, nullable = false )
    private String email;

    @Column(nullable = false)
    private boolean isAuthenticated;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    // One-to-Many: Events created by the user (as an organizer)
    @OneToMany(mappedBy = "organizer")
//    @JsonManagedReference("user-organizedEvents")
    private Set<Event> organizedEvents = new HashSet<>();

    // Many-to-Many: Events the user volunteers for
    @ManyToMany(mappedBy = "volunteers")
//    @JsonManagedReference("user-volunteeredEvents")
    private Set<Event> volunteeredEvents = new HashSet<>();

    public User() {}

    public User(String userName, String password, String email, boolean isAuthenticated, String name, UserRole role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.isAuthenticated = isAuthenticated;
        this.name = name;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<Event> getOrganizedEvents() {
        return organizedEvents;
    }

    public void setOrganizedEvents(Set<Event> organizedEvents) {
        this.organizedEvents = organizedEvents;
    }

    public Set<Event> getVolunteeredEvents() {
        return volunteeredEvents;
    }

    public void setVolunteeredEvents(Set<Event> volunteeredEvents) {
        this.volunteeredEvents = volunteeredEvents;
    }

}
