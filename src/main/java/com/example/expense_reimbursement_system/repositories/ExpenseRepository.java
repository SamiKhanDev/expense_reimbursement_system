package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {}
