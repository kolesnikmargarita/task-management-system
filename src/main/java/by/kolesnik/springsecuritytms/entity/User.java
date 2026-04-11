package by.kolesnik.springsecuritytms.entity;

import by.kolesnik.springsecuritytms.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Entity(name = "users")
@Table(name = "users")
@Data
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") // todo: notnull
    private String name;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password") // todo: notnull
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @ManyToMany(mappedBy = "users")
    private Collection<Group> groups;

    @OneToMany(mappedBy = "assignedUser", fetch = FetchType.LAZY)
    private Collection<Task> tasks;
}
