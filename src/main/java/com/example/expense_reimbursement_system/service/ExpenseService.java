package com.example.expense_reimbursement_system.service;

import com.example.expense_reimbursement_system.entities.*;
import com.example.expense_reimbursement_system.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseStatusRepository expenseStatusRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryPackageRepository categoryPackageRepository;

    @Autowired
    private RoleExpenseLimitRepository roleCategoryPackageRepository;

    // ===========================
    // POST Endpoints
    // ===========================

    /**
     * Save a new CategoryPackage.
     * @param categoryPackage the category package to save.
     * @return the saved CategoryPackage.
     */
    public CategoryPackage addCategoryPackage(CategoryPackage categoryPackage) {
        return categoryPackageRepository.save(categoryPackage);
    }

    /**
     * Save a new RoleCategoryPackage.
     * @param roleCategoryPackage the role-category package to save.
     * @return the saved RoleCategoryPackage.
     */
    public RoleCategoryPackage addRoleCategoryPackage(RoleCategoryPackage roleCategoryPackage) {
        return roleCategoryPackageRepository.save(roleCategoryPackage);
    }

    /**
     * Add a new role.
     * @param role the role to save.
     * @return the saved Role.
     */
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Create a new employee.
     * @param employee the employee to create.
     * @return the created Employee.
     */
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Add a new category.
     * @param categories the category to save.
     * @return the saved Categories.
     */
    public Categories addCategory(Categories categories) {
        return categoryRepository.save(categories);
    }

    /**
     * Create a new expense for an employee.
     * @param expense the expense to create.
     * @return the created Expense.
     */
    public Expense createExpense(Expense expense) {
        // Assuming employee, category, and status have already been set in the controller
        return expenseRepository.save(expense); // Save and return the expense
    }


    // ===========================
    // GET Endpoints
    // ===========================

    /**
     * Retrieve all packages for a specific category.
     * @param categoryId the ID of the category.
     * @return a list of CategoryPackage.
     */
    public List<CategoryPackage> getPackagesByCategory(Long categoryId) {
        return categoryPackageRepository.findAll()
                .stream()
                .filter(pkg -> pkg.getCategory().getId().equals(categoryId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all pending expenses for manager review.
     * @return a list of pending Expense objects.
     */
    public List<Expense> getPendingExpenses() {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getStatus().getId().equals(1L)) // 1 = Pending
                .collect(Collectors.toList());
    }

    /**
     * Retrieve all category packages.
     * @return a list of CategoryPackage objects.
     */
    public List<CategoryPackage> getCategoryPackage() {
        return categoryPackageRepository.findAll();
    }

    public List<Categories> getCategories(){
        return categoryRepository.findAll();
    }

    /**
     * Get all expenses of an employee within a specific date range.
     * @param employeeId the ID of the employee.
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return a list of Expense objects within the date range.
     */
    public List<Expense> getEmployeeExpenseStatus(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getEmployee().getId().equals(employeeId) &&
                        !expense.getSubmitDate().toLocalDate().isBefore(startDate) &&
                        !expense.getSubmitDate().toLocalDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    /**
     * Get expense history filtered by status.
     * @param statusId the ID of the status to filter by.
     * @return a sorted list of Expense objects by category name and submit date.
     */
    public List<Expense> getExpenseHistoryByStatus(Long statusId) {
        return expenseRepository.findAll().stream()
                .filter(expense -> expense.getStatus().getId().equals(statusId))
                .sorted(Comparator.comparing((Expense expense) -> expense.getCategory().getName())
                        .thenComparing(Expense::getSubmitDate))
                .collect(Collectors.toList());
    }

    public List<Expense> getExpenseHistory(){
        return expenseRepository.findAll();
    }


    /**
     * Get the remaining expense limit for an employee.
     * @param employeeId the ID of the employee.
     * @return the remaining amount as an Integer.
     */
    public Integer getRemainingAmountForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        List<Expense> employeeExpenses = expenseRepository.findAllByEmployeeId(employeeId);

        RoleCategoryPackage roleCategoryPackage = roleCategoryPackageRepository.findByRoleId(employee.getRole().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category package not found for role ID: " + employee.getRole().getId()));

        CategoryPackage categoryPackage = roleCategoryPackage.getCategoryPackage();

        Integer totalExpenses = employeeExpenses.stream()
                .filter(expense -> expense.getCategory().getId().equals(categoryPackage.getCategory().getId()))
                .mapToInt(Expense::getAmount)
                .sum();

        int remainingAmount = categoryPackage.getExpenseLimit() - totalExpenses;

        return Math.max(remainingAmount, 0);
    }

    // ===========================
    // PATCH Endpoints
    // ===========================

    /**
     * Update the status of an expense (Approve/Reject).
     * @param expenseId the ID of the expense to update.
     * @param statusId the ID of the new status.
     */
    public void updateExpenseStatus(Long expenseId, Long statusId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        ExpenseStatus status = expenseStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Status not found"));

        expense.setStatus(status);
        expense.setApprovalDate(LocalDateTime.now());
        expenseRepository.save(expense);
    }

    /**
     * Validate if an expense amount is within the limit for the given role and category package.
     * @param roleId the ID of the role.
     * @param categoryPackageId the ID of the category package.
     * @param expenseAmount the expense amount to validate.
     * @return true if valid, false otherwise.
     */
    public boolean validateExpenseLimit(Long roleId, Long categoryPackageId, Integer expenseAmount) {
        Optional<RoleCategoryPackage> roleCategoryPackage = roleCategoryPackageRepository.findById(categoryPackageId);
        if (roleCategoryPackage.isPresent() &&
                roleCategoryPackage.get().getRole().getId().equals(roleId)) {
            return expenseAmount <= roleCategoryPackage.get().getCategoryPackage().getExpenseLimit();
        }
        return false;
    }
}
