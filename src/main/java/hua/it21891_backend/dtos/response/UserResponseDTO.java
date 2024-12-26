package hua.it21891_backend.dtos.response;

public record UserResponseDTO (
        int userId,
        String userName,
        String email,
        String name,
        String roleName
){}


