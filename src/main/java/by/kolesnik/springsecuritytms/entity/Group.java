package by.kolesnik.springsecuritytms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;


@Entity(name = "groups")
@Table(name = "groups")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Collection<User> users;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Collection<Task> tasks;
}
