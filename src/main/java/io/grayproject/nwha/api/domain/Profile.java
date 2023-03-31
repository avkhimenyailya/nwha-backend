package io.grayproject.nwha.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

/**
 * @author Ilya Avkhimenya
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column
    private String description;

    @OneToMany(mappedBy = "profile")
    private List<ProfileTask> profileTasks;

    @OneToMany(mappedBy = "profile")
    private List<ProfileTrait> profileTraits;

    @Column
    private Boolean removed;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", description='" + description + '\'' +
                ", removed=" + removed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return new EqualsBuilder()
                .append(id, profile.id)
                .append(user.getId(), profile.user.getId())
                .append(description, profile.description)
                .append(profileTasks.size(), profile.profileTasks.size())
                .append(profileTraits.size(), profile.profileTraits.size())
                .append(removed, profile.removed)
                .append(createdAt, profile.createdAt)
                .append(updatedAt, profile.updatedAt).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(user.getId())
                .append(description)
                .append(profileTasks.size())
                .append(profileTraits.size())
                .append(removed)
                .append(createdAt)
                .append(updatedAt).toHashCode();
    }
}