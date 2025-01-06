package com.example.expense_reimbursement_system.rest;

import com.example.expense_reimbursement_system.dto.ExpenseDTO;
import com.example.expense_reimbursement_system.dto.ExpenseValidationRequest;
import com.example.expense_reimbursement_system.dto.UpdateExpenseStatusRequest;
import com.example.expense_reimbursement_system.entities.*;
import com.example.expense_reimbursement_system.repositories.CategoryRepository;
import com.example.expense_reimbursement_system.repositories.EmployeeRepository;
import com.example.expense_reimbursement_system.repositories.ExpenseStatusRepository;
import com.example.expense_reimbursement_system.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ExpenseStatusRepository expenseStatusRepository;

    // ----------------------------------
    // POST Endpoints
    // ----------------------------------

    /**
     * Create a new expense for an employee.
     * @return Saved Expense object.
     */
    @PostMapping("expense")
    public ResponseEntity<Expense> createExpenseStatus(@RequestBody ExpenseDTO expenseDTO) {
        if (expenseDTO == null) {
            return ResponseEntity.badRequest().build();
        }

        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());

        // Fetch the employee from the database using the employeeId from the DTO
        Employee employee = employeeRepository.findById(expenseDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + expenseDTO.getEmployeeId()));
        expense.setEmployee(employee);

        // Fetch the category using the categoryId from the DTO
        Categories category = categoryRepository.findById(expenseDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + expenseDTO.getCategoryId()));
        expense.setCategory(category);

        // Set the status (e.g., Pending) as before
        ExpenseStatus pendingStatus = expenseStatusRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Status not found with ID: 1"));
        expense.setStatus(pendingStatus);

        // Set the submit date
        if (expense.getSubmitDate() == null) {
            expense.setSubmitDate(LocalDateTime.now());
        }

        // Save and return the expense
        Expense savedExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(savedExpense);
    }


    /**
     * Add a new CategoryPackage.
     * @param categoryPackage CategoryPackage object.
     * @return Saved CategoryPackage object.
     */
    @PostMapping("/add-category-package")
    public ResponseEntity<CategoryPackage> addCategoryPackage(@RequestBody CategoryPackage categoryPackage) {
        CategoryPackage savedPackage = expenseService.addCategoryPackage(categoryPackage);
        return ResponseEntity.ok(savedPackage);
    }

    /**
     * Add a new RoleCategoryPackage.
     * @param roleCategoryPackage RoleCategoryPackage object.
     * @return Saved RoleCategoryPackage object.
     */
    @PostMapping("/add-role-category-package")
    public ResponseEntity<RoleCategoryPackage> addRoleCategoryPackage(@RequestBody RoleCategoryPackage roleCategoryPackage) {
        RoleCategoryPackage savedPackage = expenseService.addRoleCategoryPackage(roleCategoryPackage);
        return ResponseEntity.ok(savedPackage);
    }

    /**
     * Add a new employee.
     * @param employee Employee object.
     * @return Saved Employee object.
     */
    @PostMapping("/addEmployee")
    public Employee addEmployee(@RequestBody Employee employee) {
        return expenseService.createEmployee(employee);
    }

    /**
     * Add a new role.
     * @param role Role object.
     * @return Saved Role object.
     */
    @PostMapping("/add-role")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role savedRole = expenseService.addRole(role);
        return ResponseEntity.ok(savedRole);
    }

    /**
     * Add a new category.
     * @param categories Categories object.
     * @return Saved Categories object.
     */
    @PostMapping("/addCategory")
    public ResponseEntity<Categories> addCategory(@RequestBody Categories categories) {
        Categories save = expenseService.addCategory(categories);
        return ResponseEntity.ok(save);
    }

    /**
     * Validate an expense based on role and category package limits.
     * @param request ExpenseValidationRequest containing role ID, category package ID, and expense amount.
     * @return Boolean indicating if the expense is valid.
     */
    @PostMapping("/validate-expense")
    public ResponseEntity<Boolean> validateExpense(@RequestBody ExpenseValidationRequest request) {
        boolean isValid = expenseService.validateExpenseLimit(request.getRoleId(), request.getCategoryPackageId(), request.getExpenseAmount());
        return ResponseEntity.ok(isValid);
    }

    // ----------------------------------
    // GET Endpoints
    // ----------------------------------

    /**
     * Get all pending expenses.
     * @return List of pending expenses.
     */
    @GetMapping("/pending")
    public ResponseEntity<List<Expense>> getPendingExpenses() {
        return ResponseEntity.ok(expenseService.getPendingExpenses());
    }

    /**
     * Get all category packages.
     * @return List of CategoryPackage objects.
     */
    @GetMapping("/category")
    public ResponseEntity<List<CategoryPackage>> getCategoryPackage() {
        return ResponseEntity.ok(expenseService.getCategoryPackage());
    }

    /**
     * Get category packages by category ID.
     * @param categoryId ID of the category.
     * @return List of CategoryPackage objects.
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CategoryPackage>> getPackagesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(expenseService.getPackagesByCategory(categoryId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categories = expenseService.getCategories();
        return ResponseEntity.ok(categories);
    }


    /**
     * Get the remaining amount for an employee.
     * @param employeeId ID of the employee.
     * @return Remaining amount as Integer.
     */
    @GetMapping("/{employeeId}/remaining-amount")
    public ResponseEntity<Integer> getRemainingAmount(@PathVariable Long employeeId) {
        Integer remainingAmount = expenseService.getRemainingAmountForEmployee(employeeId);
        return ResponseEntity.ok(remainingAmount);
    }

    /**
     * Get the expense status for an employee within a date range.
     * @param employeeId ID of the employee.
     * @param startDate Start date in YYYY-MM-DD format.
     * @param endDate End date in YYYY-MM-DD format.
     * @return List of expenses.
     */
    @GetMapping("/status/{employeeId}")
    public ResponseEntity<List<Expense>> getEmployeeExpenseStatus(
            @PathVariable Long employeeId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(expenseService.getEmployeeExpenseStatus(employeeId, start, end));
    }

    /**
     * Get expense history filtered by status.
     * @param statusId ID of the status.
     * @return List of expenses.
     */
    @GetMapping("/history/{statusId}")
    public ResponseEntity<List<Expense>> getExpenseHistoryByStatus(@PathVariable Long statusId) {
        return ResponseEntity.ok(expenseService.getExpenseHistoryByStatus(statusId));
    }

    // ----------------------------------
    // PATCH Endpoints
    // ----------------------------------

    /**
     * Update the status of an expense.
     * @param request UpdateExpenseStatusRequest containing expense ID and status ID.
     * @return Success message.
     */
    @PatchMapping("/status")
    public ResponseEntity<String> updateExpenseStatus(@RequestBody UpdateExpenseStatusRequest request) {
        Long expenseId = request.getExpenseId();
        Long statusId = request.getStatusId();

        // Validate statusId
        if (!isValidStatusId(statusId)) {
            throw new IllegalArgumentException("Invalid status ID. Allowed values are 4 (Approved) and 5 (Rejected).");
        }

        expenseService.updateExpenseStatus(expenseId, statusId);
        return ResponseEntity.ok("Expense status updated successfully.");
    }

    /**
     * Validate if the status ID is allowed.
     * @param statusId ID of the status.
     * @return Boolean indicating if the status ID is valid.
     */
    private boolean isValidStatusId(Long statusId) {
        return statusId == 4 || statusId == 5;
    }
}
