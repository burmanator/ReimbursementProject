package com.ex.daos;

import com.ex.pojos.Employee;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class provide the interaction between the Object Employee
 * and the MongoDatabase. This class implements validation, adding
 * updating username, updating password, updating email,finding an
 *  Employee, getting the password of the employee, and the retrieval
 *  of all existing employees.
 */
public class EmployeeDao {
    private MongoClient mongoClient;
    private DBCollection employees;
    Logger logger = LogManager.getLogger(EmployeeDao.class);

    public EmployeeDao() throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("Project1");
        employees = database.getCollection("employees");

    }
    public EmployeeDao(String name) throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB(name);
        employees = database.getCollection("employees");
    }

    /**
     * This method validates an employee's username and password by checking into the
     * database.
     * @param username from which the session user is attempting to login
     * @param password from which the session user is attempting to login
     * @return
     */
    public boolean validateEmployee(String username, String password){
        System.out.println(username+password);
        DBObject query = new BasicDBObject("username", username).append("password", password);
        DBObject employee = employees.findOne(query);
        if(employee!=null){
            logger.info(username+" successfully logged in");
            return true;
        }
        else {
            logger.info(username+" failed an attempt to log in");
            return false;
        }
    }

    /**
     * This method inserts a new Employee into the databse
     * @param employee is whom the method shall insert into the database
     * @return
     */
    public boolean addEmployee(Employee employee){
        DBObject user = new BasicDBObject("username",employee.getUsername()).append("password", employee.getPassword())
                .append("email", employee.getEmail());
        employees.insert(user);
        return true;
    }

    /**
     * This method will find the existing employee and shall update the username of the Employee
     * @param employee who's username shall be updated
     * @param newUsername is the new username that will be assigned to the Employee
     * @return true if successful
     */
    public boolean updateEmployeeUsername(Employee employee, String newUsername) {
        DBObject query = new BasicDBObject("username", employee.getUsername());
        DBCursor cursor = employees.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password = obj.get("password").toString();
            employees.remove(query);
            Employee updatedUsername = new Employee(newUsername,employee.getEmail(),password);
            boolean add = addEmployee(updatedUsername);
            if(add){
                return true;
            }
        }
        return false;
    }

    /**
     * This method will find the existing employee and shall update the email of the Employee
     *
     * @param employee who's email shall be updated
     * @param newEmail is the new email that will be assigned to the Employee
     * @return true if successful
     */
    public boolean updateEmployeeEmail(Employee employee, String newEmail) {
        //get the employee
        DBObject query = new BasicDBObject("username", employee.getUsername());
        DBCursor cursor = employees.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password = obj.get("password").toString();
            employees.remove(query);
            Employee updatedEmail = new Employee(employee.getUsername(),newEmail,password);
            boolean add = addEmployee(updatedEmail);
            System.out.println(add);
            return true;
        }
        return false;
    }

    /**
     * This method will find the existing employee that matches the old password
     * and shall update the password of the Employee
     * @param employee who's password shall be updated
     * @param oldPassword is the old password that will be checked with by the method
     * @param newPassword is the new password that will be assigned to the Employee
     * @return true if successful
     */
    public boolean updateEmployeePassword(Employee employee,String oldPassword, String newPassword) {
        DBObject query = new BasicDBObject("username", employee.getUsername());
        DBCursor cursor = employees.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password =obj.get("password").toString();
            //password entered is not correct
            if(!oldPassword.equals(password)){
                return false;
            }
            String email = obj.get("email").toString();
            employees.remove(query);
            Employee updatedPassword = new Employee(employee.getUsername(),email,newPassword);
            boolean add = addEmployee(updatedPassword);
            System.out.println(add);
            return true;
        }
        return false;
    }
    /**
     * This method shall find an employee that has the username in the database
     *
     * @param username of which the method will query the database
     * @return an Employee who matches the username
     */
    public Employee findEmployee(String username){
        DBObject user = new BasicDBObject("username",username);
        DBCursor cursor = employees.find(user);
        if(cursor.hasNext()){
            Employee employee = new Employee();
            DBObject obj= cursor.next();
            employee.setUsername(obj.get("username").toString());
            employee.setEmail(obj.get("email").toString());
            return employee;
            }
        return null;
    }

    /**
     * The method shall return all employees who match the given
     * name parameter
     * @param name the parameter which must be met to be part of the employees returned
     * @return a list of employees matching the search parameter
     */
    public ArrayList<Employee> getAllEmployees(String name){
        DBObject user;
        System.out.println("code was here outside" + name+ "");
        if(name.equals("*")){
            System.out.println("code was here");
            user = new BasicDBObject();
            DBCursor cursor = employees.find(user);
            ArrayList<Employee> employees = new ArrayList<>();
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = (String) obj.get("username");
                String email = (String) obj.get("email");
                Employee employee = new Employee(username, email, "");
                employees.add(employee);
            }
            return employees;
        }
        else{
            user = new BasicDBObject("username",Pattern.compile(name, Pattern.CASE_INSENSITIVE));
            DBCursor cursor = employees.find(user);
            ArrayList<Employee> employees = new ArrayList<>();
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String username = (String) obj.get("username");
                String email = (String) obj.get("email");
                Employee employee = new Employee(username, email, "");
                employees.add(employee);
            }
            return employees;
        }
    }
    /**
     * This method shall get the password of the given username
     * @param username the parameter from which to find the password
     * @return a string of the password
     */
    public String getPassword(String username) {
        DBObject query = new BasicDBObject("username", username);
        DBObject obj= employees.findOne(query);
        return obj.get("password").toString();
    }
}
