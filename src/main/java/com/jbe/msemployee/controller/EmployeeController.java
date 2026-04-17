package com.jbe.msemployee.controller;

import static com.jbe.msemployee.commons.Constants.*;
import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = SWAGGER_TAG_NAME, description = SWAGGER_TAG_DESC)
@Validated
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = SWAGGER_OP_CREATE, description = SWAGGER_OP_CREATE_DESC)
    @ApiResponse(responseCode = "201", description = "Empleado(s) creado(s) exitosamente")
    @PostMapping
    // aquí pido directamente la lista, jackson se encarga de envolverlo si solo mandan un objeto
    public ResponseEntity<List<EmployeeResponseDTO>> create(@Valid @RequestBody List<EmployeeRequestDTO> requests) {
        // delego todo a mi método de lote en el servicio
        return new ResponseEntity<>(service.createEmployees(requests), HttpStatus.CREATED);
    }

    @Operation(summary = SWAGGER_OP_GET_ALL, description = SWAGGER_OP_GET_ALL_DESC)
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @Operation(summary = SWAGGER_OP_GET_ID)
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @Operation(summary = SWAGGER_OP_SEARCH)
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchEmployeesByName(name));
    }

    @Operation(summary = SWAGGER_OP_PUT, description = SWAGGER_OP_PUT_DESC)
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(service.updateEmployee(id, request));
    }

    @Operation(summary = SWAGGER_OP_PATCH, description = SWAGGER_OP_PATCH_DESC)
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> patch(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(service.patchEmployee(id, request));
    }

    @Operation(summary = SWAGGER_OP_DELETE, description = SWAGGER_OP_DELETE_DESC)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}