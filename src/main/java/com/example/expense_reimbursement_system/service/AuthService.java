package com.example.expense_reimbursement_system.service;

import com.example.expense_reimbursement_system.entities.Employee;
import com.example.expense_reimbursement_system.repositories.EmployeeRepository;
import com.example.expense_reimbursement_system.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String login(String username, String password) {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Validate the password
        if (!passwordEncoder.matches(password, employee.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Generate JWT token
        return JwtUtil.generateToken(employee.getUsername(), employee.getRole().getName());
    }
}
