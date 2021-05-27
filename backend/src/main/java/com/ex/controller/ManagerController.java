package com.ex.controller;

import com.ex.daos.AdminDao;
import com.ex.daos.ReimbursementDao;
import com.ex.pojos.Admin;
import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import io.javalin.Javalin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class ManagerController {
    Logger logger = LogManager.getLogger(Javalin.class);
    AdminDao adminDao = new AdminDao();
    ReimbursementDao reimbursementDao = new ReimbursementDao();

    public ManagerController() throws UnknownHostException {
    }

    public int login(Admin admin){
        boolean validated = adminDao.validateAdmin(admin.getUsername(), admin.getPassword());
        if(validated){
            //user was successfully validated
            return 200;
        }
        else{
            return 401;
        }
    }

    public int addAdmin(Admin admin){
        //check if the username already exist
        Admin found =adminDao.findAdmin(admin.getUsername());
        if(!found.equals(null)){
            //employee already exist
            return 401;
        }
        else{
            //if not create new admin
            if(adminDao.addAdmin(admin)){
                logger.info(admin.getUsername()+ " account created as an admin");
                return 200;
            }
            else{
                return 401;
            }
        }
    }

    public ArrayList<Reimbursement> viewAllPending(String name){
        return reimbursementDao.getAllPendingReimbursements(name);
    }

    public ArrayList<Reimbursement> getAllPreviousReimbursements(String name){
        return reimbursementDao.getAllPrevReimbursements(name);
    }

    public int updateReimbursement(Reimbursement reimbursement){
        if(reimbursementDao.updateReimbursement(reimbursement)){
            return 200;
        }
        return 404;
    }

    public String getEmail(String username){
        Admin admin = adminDao.findAdmin(username);
        return admin.getEmail();
    }

    public int updateAdminUsername(String currUsername, String newUsername) {
        //check if newUsername exist
        Admin admin = adminDao.findAdmin(newUsername);
        //if newUsername does not exist, update currUsername's username to newUsername
        if(admin==null){
            Admin currAdmin = adminDao.findAdmin(currUsername);
            //update username of employee
            boolean update = adminDao.updateAdminUsername(currAdmin, newUsername);
            if(update){
                return 200;
            }
            else{
                return 404;
            }
        }
        return 404;
    }

    public int updateAdminEmail(String username, String newEmail) {
        //check if newUsername exist
        Admin admin = adminDao.findAdmin(username);
//        System.out.println(employee.getUsername());
        boolean update = adminDao.updateAdminEmail(admin, newEmail);
        if(update){
            return 200;
        }
        else{
            return 404;
        }
    }

    public int updateAdminPassword(String username, String password, String newPassword1) {
        //check if newUsername exist
        Admin admin = adminDao.findAdmin(username);
        boolean update = adminDao.updateAdminPassword(admin, password,newPassword1);
        if(update){
            return 200;
        }
        return 404;
    }

    public boolean updateForgottenPassword(String username, String newPassword1) {
        Admin admin = adminDao.findAdmin(username);
        String oldPassword = adminDao.getPassword(username);
        return adminDao.updateAdminPassword(admin, oldPassword,newPassword1);
    }

    public int forgotPassword(Admin admin) {
        String username = admin.getUsername();
        String email = admin.getEmail();
        Admin foundAdmin =adminDao.findAdmin(username);
        if(foundAdmin!=null){
            if(foundAdmin.getEmail().equals(email)){
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
}
