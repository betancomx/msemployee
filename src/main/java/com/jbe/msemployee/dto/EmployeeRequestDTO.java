package com.jbe.msemployee.dto;

import com.jbe.msemployee.entity.Puesto;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record EmployeeRequestDTO(
        @NotBlank(message = "El primer nombre no puede ir vacio")
        String firstName,
        String middleName,
        @NotBlank(message = "El apellido paterno es obligatorio")
        String lastName,
        @NotBlank(message = "El apellido meterno es obligatorio")
        String secondLastName,
        @Min(value = 18, message = "No puede haber empleados menores de edad")
        Integer age,
        @NotBlank(message = "Falta el sexo")
        String gender,
        // pa que jackson agarre el formato dd-MM-yyyy que pidieron
        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento no puede ser mayor al día actual")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthDate,
        @NotNull(message = "El empleado debe tener un puesto")
        Puesto puesto
) {}
