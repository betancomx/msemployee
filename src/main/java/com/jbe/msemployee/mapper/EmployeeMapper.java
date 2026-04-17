package com.jbe.msemployee.mapper;

import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequestDTO dto) {
        Integer calculatedAge = calculateAge(dto.birthDate());

        return Employee.builder()
                .firstName(dto.firstName())
                .middleName(dto.middleName())
                .lastName(dto.lastName())
                .secondLastName(dto.secondLastName())
                .age(calculatedAge) // Guardo el calculo en el campo de la BD
                .gender(dto.gender())
                .birthDate(dto.birthDate())
                .puesto(dto.puesto())
                .isActive(true)
                .build();
    }

    public EmployeeResponseDTO toDto(Employee entity) {
        return new EmployeeResponseDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getAge(),
                entity.getGender().name(),
                entity.getBirthDate(),
                entity.getPuesto(),
                entity.getCreatedAt()
        );
    }

    private Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return java.time.Period.between(birthDate, LocalDate.now()).getYears();
    }
}