package io.grayproject.nwha.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;

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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @ManyToOne
    @JoinColumn(name = "profile_task_id", nullable = false)
    private ProfileTask profileTask;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", optionId=" + option.getId() +
                ", profileTaskId=" + profileTask.getId() +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return new EqualsBuilder()
                .append(id, answer.id)
                .append(option.getId(), answer.option.getId())
                .append(profileTask.getId(), answer.profileTask.getId())
                .append(createdAt, answer.createdAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(option.getId())
                .append(profileTask.getId())
                .append(createdAt)
                .toHashCode();
    }
}
