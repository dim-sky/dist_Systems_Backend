package hua.it21891_backend.dtos.request;

public record EventCreateRequest(

        String eventName,
        String eventDescription,
        String eventLocation,
        String eventDate,
        String  eventStartTime,
        int eventMaxNumberOfPeople
){}
