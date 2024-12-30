package com.example.expense_reimbursement_system.dto;

public class UpdateExpenseStatusRequest {
    private Long expenseId;
    private Long statusId;

    // Getters and Setters
    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
