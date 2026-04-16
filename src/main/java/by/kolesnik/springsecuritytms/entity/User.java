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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.ROLE_USER;

    @ManyToMany(mappedBy = "users")
    private Collection<Group> groups;

    @OneToMany(mappedBy = "assignedUser", fetch = FetchType.LAZY)
    private Collection<Task> tasks;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Task> createdTasks;
}
