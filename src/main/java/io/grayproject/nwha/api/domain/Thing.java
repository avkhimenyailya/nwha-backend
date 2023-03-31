package io.grayproject.nwha.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * @author Ilya Avkhimenya
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "things")
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "profile_task_id")
    private ProfileTask profileTask;

    @Column
    private String fileUrl;

    @Column
    private String description;

    @Column
    private boolean archived;

    @Column
    private boolean removed;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
