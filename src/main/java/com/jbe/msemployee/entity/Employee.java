package com.jbe.msemployee.entity;

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
    private String gender;
    // el formato dd-MM-yyyy lo formateo en el dto
    private LocalDate birthDate;
    // guardamos el texto tal cual
    @Enumerated(EnumType.STRING)
    private Puesto puesto;
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // mi borrado logico por default
    @Builder.Default
    private Boolean isActive = true;

    // asisgno la fecha justo antes de guardar en bd
    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}