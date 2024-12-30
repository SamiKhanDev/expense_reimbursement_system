package com.example.expense_reimbursement_system.repositories;
import com.example.expense_reimbursement_system.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByEmployeeId(Long employeeId);



}
