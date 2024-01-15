package com.epam.esm.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tag name must not be blank")
    private String name;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnoreProperties("tags")
    private Set<GiftCertificate> giftCertificates;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
