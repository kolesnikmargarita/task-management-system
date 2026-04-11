package by.kolesnik.springsecuritytms.dto.user;

import by.kolesnik.springsecuritytms.enums.Role;
import lombok.Data;

@Data
public class UserGetBasicDto {

    private Long id;
    private String name;
    private Role role;
}
