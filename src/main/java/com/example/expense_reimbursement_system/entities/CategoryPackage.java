package com.example.expense_reimbursement_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "category_package")
public class CategoryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;

    @Column(name = "package_name", nullable = false, length = 50)
    private String packageName;

    @Column(name = "expense_limit", nullable = false)
    private Integer expenseLimit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Integer getExpenseLimit() {
        return expenseLimit;
    }

    public void setExpenseLimit(Integer expenseLimit) {
        this.expenseLimit = expenseLimit;
    }
}
