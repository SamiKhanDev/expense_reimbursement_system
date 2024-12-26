package com.example.expense_reimbursement_system.rest;

import com.example.expense_reimbursement_system.entities.*;
import com.example.expense_reimbursement_system.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


     // Submit a new expense request.

    @PostMapping("/submit")
    public ResponseEntity<Expense> submitExpense(@RequestBody @Valid Expense expense) {
        return ResponseEntity.ok(expenseService.submitExpense(expense));
    }


     // Get all pending expense requests.

    @GetMapping("/pending")
    public ResponseEntity<List<Expense>> getPendingExpenses() {
        return ResponseEntity.ok(expenseService.getPendingExpenses());
    }


     // Update the status of an expense request.

    @PatchMapping("/status")
    public ResponseEntity<String> updateExpenseStatus(@RequestBody Map<String, Long> request) {
        Long expenseId = request.get("expenseId");
        Long statusId = request.get("statusId");
        expenseService.updateExpenseStatus(expenseId, statusId);
        return ResponseEntity.ok("Expense status updated successfully.");
    }


     // Get the status of expenses for an employee within a specific date range.

    @GetMapping("/status/{employeeId}")
    public ResponseEntity<List<Expense>> getEmployeeExpenseStatus(
            @PathVariable Long employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(expenseService.getEmployeeExpenseStatus(employeeId, start, end));
    }


     // Get expense history filtered by status.

    @GetMapping("/history/{statusId}")
    public ResponseEntity<List<Expense>> getExpenseHistoryByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(expenseService.getExpenseHistoryByStatus(statusId));
    }
}
