package com.jbe.msemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jbe.msemployee.commons.Gender;
import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.commons.Puesto;
import com.jbe.msemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// 1. Nueva importación para Mockito en Spring Boot 3.4.0+
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// le digo a spring que solo levante el contexto web para este controlador
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 2. Usamos la nueva anotación en lugar de @MockBean
    @MockitoBean
    private EmployeeService service;

    private ObjectMapper objectMapper;
    private EmployeeResponseDTO responseDTO;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // 3. Corrijo la forma en que asigno el Puesto (ya no uso 'new')
        requestDTO = new EmployeeRequestDTO(
                "Juan", "Carlos", "Perez", "Gomez",
                Gender.H, LocalDate.of(1990, 1, 1), Puesto.DEVELOPER // <- AJUSTA ESTE VALOR A TU ENUM
        );

        responseDTO = new EmployeeResponseDTO(
                1L, "Juan", "Carlos", "Perez", "Gomez", 36, "H",
                LocalDate.of(1990, 1, 1), Puesto.DEVELOPER, // <- AJUSTA ESTE VALOR A TU ENUM
                LocalDateTime.now()
        );
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(service.getAllEmployees()).thenReturn(List.of(responseDTO));

        // le pego al endpoint y espero un 200 ok
        mockMvc.perform(get("/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Juan"));
    }

    @Test
    void testCreateEmployee() throws Exception {
        when(service.createEmployees(any())).thenReturn(List.of(responseDTO)); //Dto en lista

        String jsonPayload = objectMapper.writeValueAsString(List.of(requestDTO)); //convierte de lista

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                //revisar posicion 0 pq respuesta en forma de lista
                .andExpect(jsonPath("$[0].firstName").value("Juan"));
    }

    @Test
    void testCreateEmployee_BadRequest() throws Exception {
        //request sin campos obligatorios para generar error
        EmployeeRequestDTO badRequest = new EmployeeRequestDTO(
                "", null, "", "", null, null, null
        );

        String jsonPayload = objectMapper.writeValueAsString(List.of(badRequest));

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        //Solo se espera 204 no content
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }
}