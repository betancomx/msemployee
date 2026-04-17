package com.jbe.msemployee.service;

import com.jbe.msemployee.commons.Gender;
import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Employee;
import com.jbe.msemployee.commons.Puesto;
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
import java.time.LocalDateTime;
import java.util.List;
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
                "Juan", null, "Perez", null, Gender.H,
                LocalDate.of(1985, 1, 1), Puesto.DEVELOPER
        );
        responseDTO = new EmployeeResponseDTO(
                1L, "Juan", null, "Perez", null, 30, "H",
                LocalDate.of(1985, 1, 1), Puesto.DEVELOPER, LocalDateTime.now()
        );
    }

    @Test
    void testCreateEmployees() {
        when(mapper.toEntity(any(EmployeeRequestDTO.class))).thenReturn(employee);
        when(repository.saveAll(anyList())).thenReturn(List.of(employee));
        when(mapper.toDto(any(Employee.class))).thenReturn(responseDTO);
        List<EmployeeResponseDTO> result = service.createEmployees(List.of(requestDTO));
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Juan", result.get(0).firstName());

        //Check una sola vez llama repositorio
        verify(repository, times(1)).saveAll(anyList());
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