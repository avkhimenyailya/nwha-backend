package io.grayproject.nwha.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Ilya Avkhimenya
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "answers_values")
public class AnswerValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trait_id", nullable = false)
    private Trait trait;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @Column(nullable = false)
    private int value;

    @Override
    public String toString() {
        return "AnswerValue{" +
                "id=" + id +
                ", traitId=" + trait.getId() +
                ", optionId=" + option.getId() +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerValue that = (AnswerValue) o;
        return new EqualsBuilder()
                .append(value, that.value)
                .append(id, that.id)
                .append(trait.getId(), that.trait.getId())
                .append(option.getId(), that.option.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(trait.getId())
                .append(option.getId())
                .append(value)
                .toHashCode();
    }
}
