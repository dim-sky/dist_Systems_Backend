package hua.it21891_backend.services;

import hua.it21891_backend.dtos.request.EventCreateRequest;
import hua.it21891_backend.dtos.response.EventResponseDTO;
import hua.it21891_backend.entities.Event;
import hua.it21891_backend.entities.User;
import hua.it21891_backend.helpers.DtoHelper;
import hua.it21891_backend.repositories.EventRepository;
import hua.it21891_backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;

import java.util.*;


@Service
public class EventService {

    private EventRepository eventRepository;
    private UserRepository userRepository;

    public EventService(final EventRepository eventRepository, final UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Event createEvent(final EventCreateRequest eventCreateRequest, Authentication authentication) {

        String username = authentication.getName();

        User creator = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ο χρήστης δεν βρέθηκε"));


        if (!creator.getRole().getRoleName().equals("ROLE_ORGANIZATION"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Μόνο οργανισμοί μπορούν να φτιάξουν event");


        // Create new event
        Event event = new Event();
        event.setEventName(eventCreateRequest.eventName());
        event.setEventDescription(eventCreateRequest.eventDescription());
        event.setEventDate(eventCreateRequest.eventDate());
        event.setEventLocation(eventCreateRequest.eventLocation());
        event.setMaxNumberOfPeople(event.getMaxNumberOfPeople());
        event.setOrganizer(creator);
        event.setMaxNumberOfPeople(eventCreateRequest.eventMaxNumberOfPeople());

        return eventRepository.save(event);

    }

    @Transactional
    public void addVolunteerToEvent(int eventId, Authentication authentication) {
        String username = authentication.getName();

        User volunteer = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ο χρήστης δεν βρέθηκε"));

        Event event = eventRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Δεν βρέθηκε event με αυτό το id"));


        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VOLUNTEER")))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Μόνο εθελοντές μπορούν να δηλώσουν συμμετοχή σε event");

//        if (eventId != event.getEventId())
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "To event_id που έστειλε ο χρήστης δεν αντιστοιχεί με το Id που εσάτλησε");

        if (!volunteer.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ο λογαριασμός δεν έχει έχει επιβεβαιωθεί!");


        // Add volunteer to event
        if (event.getVolunteers() == null)
            event.setVolunteers(new HashSet<>(Arrays.asList()));

        if (event.getVolunteers().contains(volunteer))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ο εθελοντής εχεί ήδη δηλώσει συμμετοχη στο παρόν event");

        if (event.getVolunteers().size() < event.getMaxNumberOfPeople()) {
            event.getVolunteers().add(volunteer);
            eventRepository.save(event);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Το event είναι πλήρες");
        }


    }

    @Transactional
    public List<EventResponseDTO> getAllEvents() {

        List<Event> allEvents = this.eventRepository.findAll();
        List<EventResponseDTO> allEventResponses = new ArrayList<>();

        if (allEvents == null || allEvents.isEmpty()) {
            return new ArrayList<>();
        }

        for (Event event : allEvents) {
            allEventResponses.add(DtoHelper.toEventResponseDTO(event));
        }

        return allEventResponses;
    }

    @Transactional
    public boolean deleteEvent(int eventId, Authentication authentication) {
        Event event = this.eventRepository.findByEventId(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Δεν βρέθηκε event"));

        User organization = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ο οργανισμός δεν βρέθηκε"));


        if (event.getOrganizer().getUserId() != organization.getUserId())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Μόνο ο διοργανωτής του event μπορεί να το διαγράψει");


        this.eventRepository.delete(event);
        return true;
    }


}





