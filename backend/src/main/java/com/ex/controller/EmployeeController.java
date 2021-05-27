package com.ex.controller;

import com.ex.daos.EmployeeDao;
import com.ex.daos.ReimbursementDao;
import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class EmployeeController {
    Logger logger = LogManager.getLogger(Javalin.class);
    EmployeeDao employeeDao = new EmployeeDao();
    ReimbursementDao reimbursementDao = new ReimbursementDao();

    public EmployeeController() throws UnknownHostException {

    }

    /**
     * This method handles all attemps for an employee to login
     * @param loginReq the credentials of the employee that is attempting to login
     * @return
     */
    public int login(Employee loginReq) {
        //check if the username and password are valid
        if(employeeDao.validateEmployee(loginReq.getUsername(),loginReq.getPassword())){
            //respond with a success
            logger.info(loginReq.getUsername()+ " logged in successfully");
            return 200;
        }
        else{
            logger.info(loginReq.getUsername()+ " failed an attempt to log in");
            return 404;
        }
    }

    /**
     * This method controls the process of retrieving all reimbursements assiocated with
     * a user that is still pending
     *
     * @param username the user's username
     * @param password an empty string
     * @return
     */
    public ArrayList<Reimbursement> viewPendingReimbursements(String username, String password)
            throws UnknownHostException {
        Employee employee = new Employee(username, password);
        return reimbursementDao.getReimbursementsOfEmployee(employee, "pending");
    }

    /**
     * This methods controls the request for all previous reimbursements from an employee
     *
     * @param username the user's username
     * @param password an empty string
     * @return
     */
    public ArrayList<Reimbursement> prevReimbursements(String username, String password){
        Employee employee = new Employee(username, password);
        return reimbursementDao.getReimbursementsOfEmployee(employee, "!pending");
    }

    public int submitNewReimbursement(Reimbursement reimbursement){
        //check if the username and password are valid
        boolean inserted = reimbursementDao.addNewReimbursement(reimbursement);
        if(inserted){
            return 200;
        }
        else{
            return 402;
        }
    }

    /**
     * This controller checks if the new username the user wants to change to
     * already exists and performs the change accordingly
     *
     * @param currUsername the user's current username
     * @param newUsername the user's desired new username
     * @return
     */
    public int updateUsername(String currUsername, String newUsername){
        //check if newUsername exist
        Employee employee = employeeDao.findEmployee(newUsername);
        //if newUsername does not exist, update currUsername's username to newUsername
        if(employee==null){
            Employee currEmployee = employeeDao.findEmployee(currUsername);
            //update username of employee
            boolean update = employeeDao.updateEmployeeUsername(currEmployee, newUsername);
            if(update){
                return 200;
            }
            else{
                return 404;
            }
        }
        return 404;
    }

    public int updateEmail(String username, String newEmail){
        //check if newUsername exist
        Employee employee = employeeDao.findEmployee(username);
//        System.out.println(employee.getUsername());
        boolean update = employeeDao.updateEmployeeEmail(employee, newEmail);
        if(update){
            return 200;
        }
        else{
            return 404;
        }
    }

    public int updatePassword(String username, String oldPassword, String newPassword1){
        //check if newUsername exist
        Employee employee = employeeDao.findEmployee(username);
        boolean update = employeeDao.updateEmployeePassword(employee, oldPassword,newPassword1);
        if(update){
            return 200;
        }
        return 404;
    }

    public int forgotPassword(Employee employee){
        String username = employee.getUsername();
        String email = employee.getEmail();
        Employee foundEmployee =employeeDao.findEmployee(username);
        if(foundEmployee!=null){
            if(foundEmployee.getEmail().equals(email)){
                return 200;
            }
            else{
                return 400;
            }
        }
        else{
            return 400;
        }

    }

    public boolean updateForgottenPassword(String username, String newPassword1){
        Employee employee = employeeDao.findEmployee(username);
        String oldPassword = employeeDao.getPassword(username);
        return employeeDao.updateEmployeePassword(employee, oldPassword,newPassword1);
    }

    public String getEmail(String username){
        Employee employee =employeeDao.findEmployee(username);
        return employee.getEmail();
    }

    public int addEmployee(Employee employee){
        //check if the username already exist
        Employee foundEmployee =employeeDao.findEmployee(employee.getUsername());
        if(foundEmployee != null){
            //employee already exist
            return 401;
        }
        else{
            //if not create new admin
            if(employeeDao.addEmployee(employee)){
                logger.info(employee.getUsername()+ " account created as an employee");
                return 200;
            }
            else{
                return 401;
            }
        }
    }

    public ArrayList<Employee> getEmployees(String name){
        return employeeDao.getAllEmployees(name);
    }



}
