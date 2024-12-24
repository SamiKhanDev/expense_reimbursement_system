package com.example.expense_reimbursement_system.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ExpenseStatus")
public class ExpenseStatus {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ExpenseStatus(int id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
