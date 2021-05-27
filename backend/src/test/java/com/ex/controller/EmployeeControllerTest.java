package com.ex.controller;

import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class EmployeeControllerTest {
    Employee employee = new Employee("beyonce", "cnD53sEBSZQ");
    EmployeeController employeeController = new EmployeeController();
    Reimbursement reimbursement = new Reimbursement("60984f064cea0896f50fef46",  "username", "title", 1.0,  "10-30-2021",
            "approved",  "admin");

    EmployeeControllerTest() throws UnknownHostException {
    }

    @Test
    void addEmployee() {
        employeeController.addEmployee(employee);
    }

    @Test
    void login() {
//        employeeController.login(employee);
    }

    @Test
    void viewPendingReimbursements() throws UnknownHostException {
        employeeController.viewPendingReimbursements("beyonce","nD53sEBSZQ");
    }

    @Test
    void prevReimbursements() {
        employeeController.prevReimbursements("beyonce","nD53sEBSZQ");
    }

    @Test
    void submitNewReimbursement() {
        employeeController.submitNewReimbursement(reimbursement);
    }

    @Test
    void updateUsername() {
        employeeController.updateUsername("cross", "ross");
        employeeController.updateUsername("ross", "cross");
    }

    @Test
    void updateEmail() {
        employeeController.updateEmail("cross", "update@email.com");
        employeeController.updateEmail("cross", "old@email.com");
    }

    @Test
    void updatePassword() {
        employeeController.updatePassword("cross","7ei53ksxdY", "7ei53ksxdZ");
        employeeController.updatePassword("cross","7ei53ksxdZ", "7ei53ksxdY");
    }

    @Test
    void forgotPassword() {
        employeeController.forgotPassword(employee);
    }

    @Test
    void updateForgottenPassword() {
        employeeController.updatePassword("cross","7ei53ksxdY", "7ei53ksxdZ");
        employeeController.updatePassword("cross","7ei53ksxdZ", "7ei53ksxdY");
    }

    @Test
    void getEmail() {
        employeeController.getEmail("cross");
    }



    @Test
    void getEmployees() {
        employeeController.getEmployees("cross");
    }
}