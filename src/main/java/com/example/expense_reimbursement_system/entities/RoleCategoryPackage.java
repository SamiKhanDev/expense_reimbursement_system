package com.example.expense_reimbursement_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "RoleCategoryPackage")
public class RoleCategoryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "category_package_id", nullable = false)
    private CategoryPackage categoryPackage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public CategoryPackage getCategoryPackage() {
        return categoryPackage;
    }

    public void setCategoryPackage(CategoryPackage categoryPackage) {
        this.categoryPackage = categoryPackage;
    }
}
