package com.jbe.msemployee.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Gestión de Empleados", description = "Endpoints para el CRUD de empleados de Raven")
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "Crear un empleado", description = "Registra un nuevo empleado en el sistema")
    @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente")
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> create(@Valid @RequestBody EmployeeRequestDTO request) {
        return new ResponseEntity<>(service.createEmployee(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Crear empleados en lote", description = "Permite la inserción masiva de empleados")
    @PostMapping("/bulk")
    public ResponseEntity<List<EmployeeResponseDTO>> createBulk(@Valid @RequestBody List<EmployeeRequestDTO> requests) {
        return new ResponseEntity<>(service.createEmployees(requests), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos", description = "Obtiene la lista completa de empleados activos e inactivos")
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @Operation(summary = "Buscar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @Operation(summary = "Búsqueda parcial por nombre")
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchEmployeesByName(name));
    }

    @Operation(summary = "Actualización total (PUT)", description = "Reemplaza todos los campos del empleado")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(service.updateEmployee(id, request));
    }

    @Operation(summary = "Actualización parcial (PATCH)", description = "Actualiza solo los campos enviados en el cuerpo")
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> patch(
            @PathVariable Long id,
            @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(service.patchEmployee(id, request));
    }

    @Operation(summary = "Borrado lógico", description = "Desactiva al empleado sin eliminarlo de la base de datos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}