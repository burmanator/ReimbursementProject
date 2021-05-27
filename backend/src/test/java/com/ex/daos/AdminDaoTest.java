package com.ex.daos;

import com.ex.daos.AdminDao;
import com.ex.pojos.Admin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;

class AdminDaoTest {
    AdminDao adminDao = new AdminDao("Project_1_Test");

    AdminDaoTest() throws UnknownHostException {
    }

    @Test
    void validateAdmin() {
        Admin admin = new Admin("admin","password", "email@gmail.com");
        adminDao.validateAdmin(admin.getUsername(), admin.getPassword());
    }

    @Test
    void addAdmin() {
        Admin admin = new Admin("admin","password",  "email@gmail.com");
        adminDao.addAdmin(admin);
    }

    @Test
    void findAdmin() {
        adminDao.findAdmin("Miguel");
    }

    @Test
    void updateAdminUsername() {
        Admin admin = new Admin("admin","password",  "email@gmail.com");
        boolean success1 = adminDao.updateAdminUsername(admin, "adminUpdateUsername");
        boolean revert = adminDao.updateAdminUsername(admin, "admin");
    }

    @Test
    void updateAdminEmail() {
        Admin admin = new Admin("admin","password",  "email@gmail.com");
        boolean success1 = adminDao.updateAdminEmail(admin, "@gmail.com");
        boolean revert = adminDao.updateAdminEmail(admin, "email@gmail.com");
    }

    @Test
    void updateAdminPassword() {
        Admin admin = new Admin("admin","password",  "email@gmail.com");
        boolean success1 = adminDao.updateAdminPassword(admin, "password", "password1");
        boolean revert = adminDao.updateAdminPassword(admin, "password1","password");
    }

    @Test
    void getPassword() {
        Admin admin = new Admin("admin","password",  "email@gmail.com");
        String password = adminDao.getPassword("admin");
    }
}