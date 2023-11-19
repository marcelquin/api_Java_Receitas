package baseAPI.API.Login.DTO;

import baseAPI.API.Login.Model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
