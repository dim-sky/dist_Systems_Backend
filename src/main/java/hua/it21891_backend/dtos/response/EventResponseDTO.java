package hua.it21891_backend.dtos.response;

public record EventResponseDTO (
    int eventId,
    String eventName,
    String eventDescription,
    String eventLocation,
    String eventDate,
    int maxNumberOfPeople,
    UserResponseDTO organizer
){}
