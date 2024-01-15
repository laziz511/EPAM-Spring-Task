package com.epam.esm.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "gift_certificates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(min = 1, max = 1000, message = "Description must be between 1 and 1000 characters")
    private String description;

    @Positive(message = "Price must be a positive value")
    @Digits(integer = 10, fraction = 2, message = "Price can have up to 10 digits, 2 of them decimal")
    private BigDecimal price;

    @Positive(message = "Duration must be a positive value")
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdatedDate;

    @NotEmpty(message = "Tags list cannot be empty")
    @Size(min = 1, max = 100, message = "Tags list must contain between 1 and 100 tags")
    @Valid
    @ManyToMany
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
