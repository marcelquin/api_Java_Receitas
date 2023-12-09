package baseAPI.API.Seguranca.DTO;

import baseAPI.API.Seguranca.Model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
