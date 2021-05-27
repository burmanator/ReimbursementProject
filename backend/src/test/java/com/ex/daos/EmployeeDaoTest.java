package com.ex.daos;

import com.ex.pojos.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {
    EmployeeDao employeeDao = new EmployeeDao("Project_1_Test");

    EmployeeDaoTest() throws UnknownHostException {
    }

    @Test
    void validateEmployee() {
//        employeeDao.validateEmployee("david","123");
//        employeeDao.validateEmployee("addEmployee","password");
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee("addEmployee", "password");
        employeeDao.addEmployee(employee);

    }

    @Test
    void updateEmployeeUsername() {
        Employee employee = new Employee("addEmployee", "password");
        employeeDao.updateEmployeeUsername(employee, "test1");
    }

    @Test
    void updateEmployeeEmail() {
        Employee employee = new Employee("updateEmployee","email", "password");
        employeeDao.updateEmployeeUsername(employee, "emailUpdate");
    }

    @Test
    void updateEmployeePassword() {
        Employee employee = new Employee("addEmployee", "password", "email");
        employeeDao.updateEmployeePassword(employee, "password", "password1");
    }

    @Test
    void findEmployee() {
        employeeDao.findEmployee("test");
//        employeeDao.findEmployee("august");
    }

    @Test
    void getAllEmployees() {
        ArrayList<Employee> test = employeeDao.getAllEmployees("*");
        ArrayList<Employee> test1 = employeeDao.getAllEmployees("add");
    }

    @Test
    void getPassword() {
        String password = employeeDao.getPassword("test1");
    }
}