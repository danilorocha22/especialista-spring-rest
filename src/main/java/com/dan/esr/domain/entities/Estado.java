package com.dan.esr.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "estados")
public class Estado implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2)
    private String sigla;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estado estado)) return false;
        return Objects.equals(getId(), estado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
