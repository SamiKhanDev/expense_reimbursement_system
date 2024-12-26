package com.example.expense_reimbursement_system.service;

import com.example.expense_reimbursement_system.entities.Expense;
import com.example.expense_reimbursement_system.entities.ExpenseStatus;
import com.example.expense_reimbursement_system.repositories.EmployeeRepository;
import com.example.expense_reimbursement_system.repositories.ExpenseRepository;
import com.example.expense_reimbursement_system.repositories.ExpenseStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseStatusRepository expenseStatusRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


     // Submit a new expense by an employee.

    public Expense submitExpense(Expense expense) {
        expense.setSubmitDate(LocalDateTime.now());
        ExpenseStatus pendingStatus = expenseStatusRepository.findById(1L) // 1 = Pending
                .orElseThrow(() -> new RuntimeException("Pending status not found"));
        expense.setStatus(pendingStatus);
        return expenseRepository.save(expense);
    }


     // Get all pending expenses for manager review.

    public List<Expense> getPendingExpenses() {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getStatus().getId().equals(1L)) // 1 = Pending
                .collect(Collectors.toList());
    }


     // Update the status of an expense (Approve/Reject).

    public void updateExpenseStatus(Long expenseId, Long statusId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        ExpenseStatus status = expenseStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));
        expense.setStatus(status);
        expense.setApprovalDate(LocalDateTime.now());
        expenseRepository.save(expense);
    }


      //Get expenses of an employee within a specific date range.

    public List<Expense> getEmployeeExpenseStatus(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getEmployee().getId().equals(employeeId) &&
                        !expense.getSubmitDate().toLocalDate().isBefore(startDate) &&
                        !expense.getSubmitDate().toLocalDate().isAfter(endDate))
                .collect(Collectors.toList());
    }


     // Get expense history filtered by status.

    public List<Expense> getExpenseHistoryByStatus(Long statusId) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getStatus().getId().equals(statusId))
                .collect(Collectors.toList());
    }
}