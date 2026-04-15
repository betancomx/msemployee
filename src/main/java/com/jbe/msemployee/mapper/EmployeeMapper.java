package com.jbe.msemployee.mapper;

import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    // de peticion a entidad para guardar en bd
    public Employee toEntity(EmployeeRequestDTO dto) {
        return Employee.builder()
                .firstName(dto.firstName())
                .middleName(dto.middleName())
                .lastName(dto.lastName())
                .secondLastName(dto.secondLastName())
                .age(dto.age())
                .gender(dto.gender())
                .birthDate(dto.birthDate())
                .puesto(dto.puesto())
                .isActive(true)
                .build();
    }

    // de entidad a dto para la salida de la api
    public EmployeeResponseDTO toDto(Employee entity) {
        return new EmployeeResponseDTO(
                entity.getId(),
                entity.getFirstName(),
                entity.getMiddleName(),
                entity.getLastName(),
                entity.getSecondLastName(),
                entity.getAge(),
                entity.getGender(),
                entity.getBirthDate(),
                entity.getPuesto(),
                entity.getCreatedAt(),
                entity.getIsActive()
        );
    }
}