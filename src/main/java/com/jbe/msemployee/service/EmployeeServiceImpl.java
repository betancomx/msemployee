package com.jbe.msemployee.service;

import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import com.jbe.msemployee.entity.Employee;
import com.jbe.msemployee.exception.EmployeeNotFoundException;
import com.jbe.msemployee.mapper.EmployeeMapper;
import com.jbe.msemployee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
        Employee employee = mapper.toEntity(request);
        Employee saved = repository.save(employee);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> requests) {
        List<Employee> employees = requests.stream()
                .map(mapper::toEntity)
                .toList();
        List<Employee> savedEmployees = repository.saveAll(employees);
        return savedEmployees.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> getAllEmployees() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + id));
        return mapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> searchEmployeesByName(String name) {
        return repository.findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(name, name)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + id));
        // put = reemplazo total
        existing.setFirstName(request.firstName());
        existing.setMiddleName(request.middleName());
        existing.setLastName(request.lastName());
        existing.setSecondLastName(request.secondLastName());
        existing.setAge(request.age());
        existing.setGender(request.gender());
        existing.setBirthDate(request.birthDate());
        existing.setPuesto(request.puesto());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO patchEmployee(Long id, EmployeeRequestDTO request) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + id));
        // patch = solo lo que no venga nulo
        if (request.firstName() != null) existing.setFirstName(request.firstName());
        if (request.middleName() != null) existing.setMiddleName(request.middleName());
        if (request.lastName() != null) existing.setLastName(request.lastName());
        if (request.secondLastName() != null) existing.setSecondLastName(request.secondLastName());
        if (request.age() != null) existing.setAge(request.age());
        if (request.gender() != null) existing.setGender(request.gender());
        if (request.birthDate() != null) existing.setBirthDate(request.birthDate());
        if (request.puesto() != null) existing.setPuesto(request.puesto());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Empleado no encontrado con ID: " + id));
        // se apaga la bandera de activo
        existing.setIsActive(false);
        repository.save(existing);
    }
}