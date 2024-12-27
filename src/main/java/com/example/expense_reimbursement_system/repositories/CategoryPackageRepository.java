package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.CategoryPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryPackageRepository extends JpaRepository<CategoryPackage, Long> {
    // Custom query methods if needed, e.g.,
    // List<CategoryPackage> findByCategoryId(Long categoryId);
}
