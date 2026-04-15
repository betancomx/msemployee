package com.jbe.msemployee.service;

import com.jbe.msemployee.dto.EmployeeRequestDTO;
import com.jbe.msemployee.dto.EmployeeResponseDTO;
import java.util.List;

public interface EmployeeService {
        EmployeeResponseDTO createEmployee(EmployeeRequestDTO request);
        List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> requests);
        List<EmployeeResponseDTO> getAllEmployees();
        EmployeeResponseDTO getEmployeeById(Long id);
        List<EmployeeResponseDTO> searchEmployeesByName(String name);
        EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO request); // reemplazo total
        EmployeeResponseDTO patchEmployee(Long id, EmployeeRequestDTO request);  // actualización parcial
        void deleteEmployee(Long id);
}