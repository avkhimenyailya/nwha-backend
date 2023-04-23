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

    @ManyToOne
    @JoinColumn(name = "profile_task_id", nullable = false)
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

    @Override
    public String toString() {
        return "Thing{" +
                "id=" + id +
                ", profileTaskId=" + profileTask.getId() +
                ", fileUrl='" + fileUrl + '\'' +
                ", description='" + description + '\'' +
                ", archived=" + archived +
                ", removed=" + removed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
