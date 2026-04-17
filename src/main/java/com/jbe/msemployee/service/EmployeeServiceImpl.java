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

import java.time.LocalDate;
import java.util.List;

import static com.jbe.msemployee.commons.Constants.MSG_EMP_NOT_FOUND;

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
        return repository.findAllByIsActiveTrue().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployeeById(Long id) {
        Employee employee = findActiveById(id);
        return mapper.toDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDTO> searchEmployeesByName(String name) {
        return repository.searchActiveByName(name)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request) {
        Employee existing = findActiveById(id);

        existing.setFirstName(request.firstName());
        existing.setMiddleName(request.middleName());
        existing.setLastName(request.lastName());
        existing.setSecondLastName(request.secondLastName());
        existing.setAge(java.time.Period.between(request.birthDate(), LocalDate.now()).getYears());
        existing.setGender(request.gender());
        existing.setBirthDate(request.birthDate());
        existing.setPuesto(request.puesto());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public EmployeeResponseDTO patchEmployee(Long id, EmployeeRequestDTO request) {
        // protejo patch contra empleados borrados
        Employee existing = findActiveById(id);

        if (request.firstName() != null) existing.setFirstName(request.firstName());
        if (request.middleName() != null) existing.setMiddleName(request.middleName());
        if (request.lastName() != null) existing.setLastName(request.lastName());
        if (request.secondLastName() != null) existing.setSecondLastName(request.secondLastName());
        if (request.gender() != null) existing.setGender(request.gender());
        if (request.birthDate() != null) existing.setBirthDate(request.birthDate());
        if (request.puesto() != null) existing.setPuesto(request.puesto());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        Employee existing = findActiveById(id);
        existing.setIsActive(false);
        repository.save(existing);
    }

    private Employee findActiveById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format(MSG_EMP_NOT_FOUND, id)));
        if (!Boolean.TRUE.equals(employee.getIsActive())) {
            throw new EmployeeNotFoundException(String.format(MSG_EMP_NOT_FOUND, id));
        }
        return employee;
    }
}