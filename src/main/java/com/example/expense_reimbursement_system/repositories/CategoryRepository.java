package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories, Long> {}
