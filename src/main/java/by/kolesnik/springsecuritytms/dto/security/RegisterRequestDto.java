package by.kolesnik.springsecuritytms.dto.security;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String username;
    private String password;
}
