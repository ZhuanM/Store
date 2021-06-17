package com.company;

import java.math.BigDecimal;

public class Employee {
    private final String id;
    private String name;
    private BigDecimal salary;

    public Employee(String id, String name, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        if (salary.compareTo(BigDecimal.ZERO) == -1) {
            this.salary = BigDecimal.ZERO;
        }
        else {
            this.salary = salary;
        }
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Employee [id: " + this.id + ", name: " + this.name + ", salary: " + this.salary + "]";
    }
}
