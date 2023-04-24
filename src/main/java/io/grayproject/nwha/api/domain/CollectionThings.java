package io.grayproject.nwha.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
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
@Table(name = "collections")
public class CollectionThings {

    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ToString.Include
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "things_to_collections",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "thing_id")
    )
    private Set<Thing> things;

    @ToString.Include
    private Boolean removed;

    @ToString.Include
    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    @ToString.Include
    @UpdateTimestamp
    private Instant updatedAt;

    @Override
    public String toString() {
        return "CollectionThings{" +
                "id=" + id +
                ", profileId=" + profile.getId() +
                ", name='" + name + '\'' +
                ", thingsIds=" + things.stream().map(Thing::getId).toList() +
                ", removed=" + removed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionThings that = (CollectionThings) o;
        return new EqualsBuilder()
                .append(id, that.id)
                .append(profile.getId(), that.profile.getId())
                .append(name, that.name)
                .append(removed, that.removed)
                .append(createdAt, that.createdAt)
                .append(updatedAt, that.updatedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(profile.getId())
                .append(name)
                .append(removed)
                .append(createdAt)
                .append(updatedAt)
                .toHashCode();
    }
}
