package com.example.expense_reimbursement_system.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Expense")
public class Expense {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column
    private int amount;

    @Column (length = 500)
    private String Description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private ExpenseStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitDate) {
        this.submitDate = submitDate;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Expense(){

    }

    public Expense(Long id, Employee employee,
                   int amount, String description,
                   Categories categories, ExpenseStatus status,
                   LocalDateTime submitDate, LocalDateTime approvalDate) {
        this.id = id;
        this.employee = employee;
        this.amount = amount;
        Description = description;
        this.categories = categories;
        this.status = status;
        this.submitDate = submitDate;
        this.approvalDate = approvalDate;
    }

    @Column(nullable = false)
    private LocalDateTime submitDate;

    @Column(nullable = false)
    private LocalDateTime approvalDate;






}
