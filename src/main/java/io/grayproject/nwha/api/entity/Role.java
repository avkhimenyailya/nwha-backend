package io.grayproject.nwha.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author Ilya Avkhimenya
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private ERole name;

    @ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.LAZY)
    private Set<User> users;
}
