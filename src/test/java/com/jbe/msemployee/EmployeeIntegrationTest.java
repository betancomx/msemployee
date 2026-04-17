package com.jbe.msemployee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbe.msemployee.commons.Gender;
import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.commons.Puesto;
import com.jbe.msemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Levanta el contexto completo de la aplicacion
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
// Forzar el uso de application-test.yml que tiene la H2
@ActiveProfiles("test")
class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); //limpiar datos de memoria
    }

    @Test
    void testFlujoCompletoCreacionEmpleado() throws Exception {
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO(
                "Carlos", null, "Sosa", "Gomez", Gender.H,
                LocalDate.of(1985, 8, 15), Puesto.DEVELOPER
        );

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(requestDTO))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].firstName").value("Carlos"))
                .andExpect(jsonPath("$[0].id").exists());

        //Validacion que está en H2
        long totalEmpleados = repository.count();
        assertEquals(1, totalEmpleados, "Debe existir exactamente 1 empleado en la base de datos H2");
    }
}