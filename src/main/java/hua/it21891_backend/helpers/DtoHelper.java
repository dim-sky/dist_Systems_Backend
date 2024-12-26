package hua.it21891_backend.helpers;

import hua.it21891_backend.dtos.response.EventResponseDTO;
import hua.it21891_backend.dtos.response.UserResponseDTO;
import hua.it21891_backend.entities.Event;
import hua.it21891_backend.entities.User;

public class DtoHelper {

    public static EventResponseDTO toEventResponseDTO(Event event) {
        return new EventResponseDTO(
                event.getEventId(),
                event.getEventName(),
                event.getEventDescription(),
                event.getEventLocation(),
                event.getEventDate(),
                event.getMaxNumberOfPeople(),
                toUserResponseDTO(event.getOrganizer())
        );
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
            user.getUserId(),
            user.getUserName(),
            user.getEmail(),
            user.getName(),
            user.getRole().getRoleName()
        );
    }
}
