package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}