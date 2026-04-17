package com.jbe.msemployee.dto;

import com.jbe.msemployee.commons.Gender;
import com.jbe.msemployee.commons.Puesto;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

import static com.jbe.msemployee.commons.Constants.*;

public record EmployeeRequestDTO(
        @NotBlank(message = MSG_VALID_FIRST_NAME)
        String firstName,
        String middleName,
        @NotBlank(message = MSG_VALID_LAST_NAME)
        String lastName,
        @NotBlank(message = MSG_VALID_SECOND_LAST_NAME)
        String secondLastName,
        @NotNull(message = MSG_VALID_GENDER)
        Gender gender,
        @NotNull(message = MSG_VALID_BIRTH_DATE)
        @Past(message = MSG_VALID_BIRTH_DATE_PAST)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
        LocalDate birthDate,
        @NotNull(message = MSG_VALID_PUESTO)
        Puesto puesto
) {}