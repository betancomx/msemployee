package com.jbe.msemployee.dto;

import com.jbe.msemployee.entity.Puesto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResponseDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        Integer age,
        String gender,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthDate,
        Puesto puesto,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime createdAt,
        Boolean isActive
) {}