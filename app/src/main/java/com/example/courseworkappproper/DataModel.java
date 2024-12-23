package com.example.courseworkappproper;

public class DataModel {
    String firstname, lastname, department, email, joiningdate;
    Integer leaves;
    Double salary;

    public DataModel(String Fname, String Lname, String Department, String Email, String Joiningdate, Integer Leaves, Double Salary) {
        this.firstname = Fname;
        this.lastname = Lname;
        this.department = Department;
        this.email = Email;
        this.joiningdate = Joiningdate;
        this.leaves = Leaves;
        this.salary = Salary;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
