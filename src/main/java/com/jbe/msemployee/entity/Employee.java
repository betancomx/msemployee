package com.jbe.msemployee.entity;

import com.jbe.msemployee.commons.Gender;
import com.jbe.msemployee.commons.Puesto;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String secondLastName;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Puesto puesto;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @Builder.Default
    private Boolean isActive = true;

    // asisgno la fecha justo antes de guardar en bd
    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}