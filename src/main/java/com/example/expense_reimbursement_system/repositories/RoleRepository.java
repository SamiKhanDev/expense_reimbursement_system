package com.example.expense_reimbursement_system.repositories;

import com.example.expense_reimbursement_system.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {}