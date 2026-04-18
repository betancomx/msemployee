package com.jbe.msemployee.repository;

import com.jbe.msemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByIsActiveTrue();

    @Query("SELECT e FROM Employee e WHERE e.isActive = true AND " +
            "(LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(e.middleName) LIKE LOWER(CONCAT('%', :name, '%')))")
    List<Employee> searchActiveByName(@Param("name") String name);
}