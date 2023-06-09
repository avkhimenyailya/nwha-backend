package io.grayproject.nwha.api.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private List<User> users;
}
