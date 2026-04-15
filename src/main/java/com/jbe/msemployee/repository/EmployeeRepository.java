package com.jbe.msemployee.repository;

import com.jbe.msemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // busca coincidencias ya sea en el primer o segundo nombre
    List<Employee> findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(String firstName, String middleName);
}