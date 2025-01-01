package com.example.expense_reimbursement_system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Expense Reimbursement API", version = "1.0"))
@SpringBootApplication
public class ExpenseReimbursementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseReimbursementSystemApplication.class, args);
	}

}
