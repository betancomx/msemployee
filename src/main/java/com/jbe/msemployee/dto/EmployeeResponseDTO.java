package com.jbe.msemployee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jbe.msemployee.commons.Puesto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static com.jbe.msemployee.commons.Constants.DATE_FORMAT;
import static com.jbe.msemployee.commons.Constants.DATE_TIME_FORMAT;

//No muestro campos nulos
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmployeeResponseDTO(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String secondLastName,
        Integer age,
        String gender,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
        LocalDate birthDate,
        Puesto puesto,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
        LocalDateTime createdAt
) {}