package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Employee;
import com.example.expense_reimbursement_system.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findExpensesByEmployeeAndSubmitDateBetween(Employee employee, LocalDateTime startDate, LocalDateTime endDate);

    List<Expense> findExpensesByStatus_Id(Long statusId);


}
