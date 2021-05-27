package com.ex.controller;

import com.ex.pojos.Admin;
import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class ManagerControllerTest {
    Admin admin = new Admin("Miguel", "KY8TxUSgJM", "email@gmail.com");
    Admin addedAdmin = new Admin("Pedro", "KY8TxUSgJM", "email@gmail.com");

    ManagerController managerController = new ManagerController();

    ManagerControllerTest() throws UnknownHostException {
    }

    @Test
    void login() {
        managerController.login(admin);
    }

    @Test
    void addAdmin() {
        managerController.addAdmin(addedAdmin);
    }

    @Test
    void viewAllPending() {
        managerController.viewAllPending("*");
        managerController.viewAllPending("da");
    }

    @Test
    void getAllPreviousReimbursements() {
        managerController.getAllPreviousReimbursements("*");
        managerController.getAllPreviousReimbursements("da");
    }
//    @Test
//    void getEmail() {
//        String email = managerController.getEmail("admin");
//    }

//    @Test
//    void updateAdminUsername() {
//        Admin admin = new Admin("Miguel", "KY8TxUSgJM", "email@gmail.com");
//        int updateUsername = managerController.updateAdminUsername("Miguel", "MiguelUpdate");
//        int updateUsernameReverse = managerController.updateAdminUsername( "MiguelUpdate","Miguel");
//    }

//    @Test
//    void updateAdminEmail() {
//        Admin admin = new Admin("Miguel", "KY8TxUSgJM", "email@gmail.com");
//        int updateUsername = managerController.updateAdminUsername("Miguel", "@email");
//        int updateUsernameReverse = managerController.updateAdminUsername( "Miguel","email@gmail.com");
//    }

//
//    @Test
//    void updateAdminPasswrod() {
//        Admin admin = new Admin("Miguel", "KY8TxUSgJM", "email@gmail.com");
//        int updatePassword = managerController.updateAdminPassword("Miguel", "KY8TxUSgJM", "email@gmail.com");
//        int updatePasswordReverse = managerController.updateAdminPassword( "Miguel", "email@gmail.com", "KY8TxUSgJM");
//    }
    @Test
    void forgotPasswrod() {
        Admin admin = new Admin("Miguel", "KY8TxUSgJM", "email@gmail.com");
        int forgotPassword = managerController.forgotPassword(admin);
    }
}