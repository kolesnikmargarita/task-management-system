package by.kolesnik.springsecuritytms.entity;

import by.kolesnik.springsecuritytms.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
