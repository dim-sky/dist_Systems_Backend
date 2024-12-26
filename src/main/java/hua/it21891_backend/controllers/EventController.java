package hua.it21891_backend.controllers;

import hua.it21891_backend.dtos.request.EventCreateRequest;
import hua.it21891_backend.dtos.response.EventResponseDTO;
import hua.it21891_backend.entities.Event;
import hua.it21891_backend.helpers.DtoHelper;
import hua.it21891_backend.services.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ORGANIZATION')")  // Only users with ORGANIZER role can create events
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventCreateRequest request,
                                             Authentication authentication) {


        Event event = eventService.createEvent(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoHelper.toEventResponseDTO(event));
    }


    @PostMapping("/{eventId}/join")
    @PreAuthorize("hasRole('VOLUNTEER')")
    public ResponseEntity<String> joinEvent(@PathVariable int eventId,
                                       Authentication authentication) {
        eventService.addVolunteerToEvent(eventId, authentication);
        return ResponseEntity.ok("Ο χρήστης προστέθηκε επιτυχώς");
    }


    @DeleteMapping("{eventId}/delete")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<String> deleteEvent(@PathVariable int eventId, Authentication authentication) {
        if (eventService.deleteEvent(eventId, authentication))
            return ResponseEntity.ok("Το event διαγράφθηκε επιτυχώς");
        else
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tο event δεν μπόρεσε να διαγραφεί");
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

}