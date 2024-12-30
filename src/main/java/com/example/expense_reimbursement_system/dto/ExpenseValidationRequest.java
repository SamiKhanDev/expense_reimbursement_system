package com.example.expense_reimbursement_system.dto;

public class ExpenseValidationRequest {
    private Long roleId;
    private Long categoryPackageId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCategoryPackageId() {
        return categoryPackageId;
    }

    public void setCategoryPackageId(Long categoryPackageId) {
        this.categoryPackageId = categoryPackageId;
    }

    public Integer getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Integer expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    private Integer expenseAmount;

    // Getters and Setters
}