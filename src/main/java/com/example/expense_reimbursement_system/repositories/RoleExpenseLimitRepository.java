package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.RoleCategoryPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleExpenseLimitRepository extends JpaRepository<RoleCategoryPackage, Long> {
    // Custom query methods if needed, e.g.,
    // Optional<RoleExpenseLimit> findByRoleId(Long roleId);
}
