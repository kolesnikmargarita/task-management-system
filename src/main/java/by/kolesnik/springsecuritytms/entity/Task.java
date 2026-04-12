package by.kolesnik.springsecuritytms.entity;


import by.kolesnik.springsecuritytms.enums.Priority;
import by.kolesnik.springsecuritytms.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Column(name = "deadline_date")
    private LocalDateTime deadlineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}
