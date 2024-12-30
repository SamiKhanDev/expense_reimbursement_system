package com.example.expense_reimbursement_system.rest;

import com.example.expense_reimbursement_system.entities.*;
import com.example.expense_reimbursement_system.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("employee/{employeeId}/expense")
    public ResponseEntity<Expense> createExpenseStatus(@PathVariable Long employeeId, @RequestBody Expense expense){
        if (expense.getSubmitDate() == null) {
            expense.setSubmitDate(LocalDateTime.now());
        }
        Expense savedExpense = expenseService.createExpense(employeeId,expense);
        return ResponseEntity.ok(savedExpense);
    }
    // Endpoint to add a CategoryPackage
    @PostMapping("/add-category-package")
    public ResponseEntity<CategoryPackage> addCategoryPackage(@RequestBody CategoryPackage categoryPackage) {
        CategoryPackage savedPackage = expenseService.addCategoryPackage(categoryPackage);
        return ResponseEntity.ok(savedPackage);
    }

    // Endpoint to add a RoleCategoryPackage
    @PostMapping("/add-role-category-package")
    public ResponseEntity<RoleCategoryPackage> addRoleCategoryPackage(@RequestBody RoleCategoryPackage roleCategoryPackage) {
        RoleCategoryPackage savedPackage = expenseService.addRoleCategoryPackage(roleCategoryPackage);
        return ResponseEntity.ok(savedPackage);
    }

    @PostMapping("/addEmployee")
    public Employee addEmployee(@RequestBody Employee employee){
        return expenseService.createEmployee(employee);
    }


    // Endpoint for adding a new role
    @PostMapping("/add-role")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        // Add the new role
        Role savedRole = expenseService.addRole(role);
        return ResponseEntity.ok(savedRole);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Categories> addCategory(@RequestBody Categories categories){
        Categories save = expenseService.addCategory(categories);
        return ResponseEntity.ok(save);
    }

    // Get all pending expense requests.
    @GetMapping("/pending")
    public ResponseEntity<List<Expense>> getPendingExpenses() {
        return ResponseEntity.ok(expenseService.getPendingExpenses());
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryPackage>> getCategoryPackage(){
        return ResponseEntity.ok(expenseService.getCategoryPackage());
    }


     // Update the status of an expense request.

    @PatchMapping("/status")
    public ResponseEntity<String> updateExpenseStatus(@RequestBody Map<String, Long> request) {
        Long expenseId = request.get("expenseId");
        Long statusId = request.get("statusId");
        expenseService.updateExpenseStatus(expenseId, statusId);
        return ResponseEntity.ok("Expense status updated successfully.");
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryPackage>> getPackagesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(expenseService.getPackagesByCategory(categoryId));
    }

    @PostMapping("/validate-expense")
    public ResponseEntity<Boolean> validateExpense(@RequestBody ExpenseValidationRequest request) {
        boolean isValid = expenseService.validateExpenseLimit(request.getRoleId(), request.getCategoryPackageId(), request.getExpenseAmount());
        return ResponseEntity.ok(isValid);
    }


    @GetMapping("/{employeeId}/remaining-amount")
    public ResponseEntity<Integer> getRemainingAmount(@PathVariable Long employeeId) {
        Integer remainingAmount = expenseService.getRemainingAmountForEmployee(employeeId);
        return ResponseEntity.ok(remainingAmount);
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
