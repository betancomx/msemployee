package com.jbe.msemployee.service;

import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Employee;
import com.jbe.msemployee.entity.Puesto;
import com.jbe.msemployee.exception.EmployeeNotFoundException;
import com.jbe.msemployee.mapper.EmployeeMapper;
import com.jbe.msemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    //Dependencias para mockear
    @Mock
    private EmployeeRepository repository;

    @Mock
    private EmployeeMapper mapper;


    @InjectMocks
    private EmployeeServiceImpl service;

    private Employee employee;
    private EmployeeRequestDTO requestDTO;
    private EmployeeResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        //Datos comunes
        employee = Employee.builder()
                .id(1L)
                .firstName("Juan")
                .lastName("Perez")
                .puesto(Puesto.DEVELOPER)
                .isActive(true)
                .build();
        requestDTO = new EmployeeRequestDTO(
                "Juan", null, "Perez", null, 30, "M",
                LocalDate.of(1985, 1, 1), Puesto.DEVELOPER
        );
        responseDTO = new EmployeeResponseDTO(
                1L, "Juan", null, "Perez", null, 30, "M",
                LocalDate.of(1985, 1, 1), Puesto.DEVELOPER, null, true
        );
    }

    @Test
    void testCreateEmployee() {
        when(mapper.toEntity(any(EmployeeRequestDTO.class))).thenReturn(employee);
        when(repository.save(any(Employee.class))).thenReturn(employee);
        when(mapper.toDto(any(Employee.class))).thenReturn(responseDTO);

        EmployeeResponseDTO result = service.createEmployee(requestDTO);

        assertNotNull(result);
        assertEquals("Juan", result.firstName());

        //Check una sola vez llama repositorio
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById_Success() {
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(mapper.toDto(any(Employee.class))).thenReturn(responseDTO);

        EmployeeResponseDTO result = service.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        //BD vacia
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> {
            service.getEmployeeById(99L);
        });
    }
}