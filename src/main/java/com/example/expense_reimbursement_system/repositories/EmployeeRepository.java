package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}