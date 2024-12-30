package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.RoleCategoryPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleExpenseLimitRepository extends JpaRepository<RoleCategoryPackage, Long> {
    Optional<RoleCategoryPackage> findByRoleId(Long roleId);

}
