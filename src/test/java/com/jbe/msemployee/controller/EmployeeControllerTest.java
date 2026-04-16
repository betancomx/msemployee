package com.jbe.msemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Puesto;
import com.jbe.msemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
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

    @MockBean
    private EmployeeService service;

    private ObjectMapper objectMapper;
    private EmployeeResponseDTO responseDTO;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        // configuro jackson pa que entienda las fechas
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        requestDTO = new EmployeeRequestDTO(
                "Juan", null, "Perez", "Vasconcelos", 30, "M",
                LocalDate.of(1993, 1, 1), Puesto.DEVELOPER
        );

        responseDTO = new EmployeeResponseDTO(
                1L, "Juan", null, "Perez", null, 30, "M",
                LocalDate.of(1993, 1, 1), Puesto.DEVELOPER, null, true
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
        // simulo la creacion exitosa
        when(service.createEmployee(any(EmployeeRequestDTO.class))).thenReturn(responseDTO);

        // mando el post con el json y espero un 201 created
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateEmployee_BadRequest() throws Exception {
        //Request erroneo, falta el apellido
        EmployeeRequestDTO badRequest = new EmployeeRequestDTO(
                "Juan", null, "Perez", null, 30, "M",
                LocalDate.of(1993, 1, 1), Puesto.DEVELOPER
        );

        // Server manda 400
        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.secondLastName").exists());
    }

    @Test
    void testDeleteEmployee() throws Exception {
        //Solo se espera 204 no content
        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isNoContent());
    }
}