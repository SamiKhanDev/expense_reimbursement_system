package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ExpenseStatusRepository extends JpaRepository<ExpenseStatus, Long> {
    ExpenseStatus findByName(String name);


}
