package hua.it21891_backend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eventId;

    @Column
    private String eventName;

    @Column
    private String eventDescription;

    @Column
    private String eventLocation;

    @Column
    private String eventDate;

    @Column
    private String eventStartTime;

    @Column
    private int maxNumberOfPeople;


    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false) // Foreign key in Event table
    private User organizer;

    // Many-to-Many: Volunteers participating in the event
    @ManyToMany
    @JoinTable(
            name = "event_volunteers",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "volunteer_id")
    )
    private Set<User> volunteers = new HashSet<>();

    public Event() {}

    public Event(int eventId, String eventName, String eventDescription, String eventLocation, String eventDate, String eventStartTime,int maxNumberOfPeople) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Set<User> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Set<User> volunteers) {
        this.volunteers = volunteers;
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }
}
