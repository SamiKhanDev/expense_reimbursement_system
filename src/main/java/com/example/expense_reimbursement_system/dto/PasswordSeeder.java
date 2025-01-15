package com.example.expense_reimbursement_system.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordSeeder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String hashedPassword1 = encoder.encode("password123"); // Employee 1
        String hashedPassword2 = encoder.encode("manager123");  // Employee 2
        String hashedPassword3 = encoder.encode("intern123");   // Employee 3

        System.out.println("Hashed Passwords:");
        System.out.println(hashedPassword1);
        System.out.println(hashedPassword2);
        System.out.println(hashedPassword3);
    }
}
