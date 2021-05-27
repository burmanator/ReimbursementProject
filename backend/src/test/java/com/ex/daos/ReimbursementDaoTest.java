package com.ex.daos;

import com.ex.pojos.Employee;
import com.ex.pojos.Reimbursement;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReimbursementDaoTest {
    ReimbursementDao reimbursementDao = new ReimbursementDao("Project_1_Test");
    Reimbursement reimbursement = new Reimbursement("60984f064cea0896f50fef46",  "username", "title", 1.0,  "10-30-2021",
            "pending",  "admin");
    Reimbursement reimbursement1 = new Reimbursement("60984f064cea0896f50fef47",  "username", "title", 1.0,  "10-30-2021",
            "pending",  "admin");
    Reimbursement updateReimbursement = new Reimbursement("60984f064cea0896f50fef46",  "username", "title", 1.0,  "10-30-2021",
            "approved",  "admin");
    Reimbursement updateReimbursement1 = new Reimbursement("60984f064cea0896f50fef47",  "username", "title", 1.0,  "10-30-2021",
            "denied",  "admin");

    ReimbursementDaoTest() throws UnknownHostException {
    }

    @Test
    void getAllPendingReimbursements() {
        ArrayList<Reimbursement> result = reimbursementDao.getAllPendingReimbursements("*");
        ArrayList<Reimbursement> result1 = reimbursementDao.getAllPendingReimbursements("dav");
    }

    @Test
    void addNewReimbursement() {
        reimbursementDao.addNewReimbursement(reimbursement);
    }


    @Test
    void getReimbursementsOfEmployee(){
        Employee employee = new Employee("test1", "password");
        Reimbursement pendingReimbursement = new Reimbursement("60984f064cea0896f50fef46", "test1","title",
                4.0,"05-13-2021", "pending", "admin");
        reimbursementDao.addNewReimbursement(pendingReimbursement);
        ArrayList<Reimbursement> pendingResult = reimbursementDao.getReimbursementsOfEmployee(employee, "pending");

        Reimbursement approvedReimbursement = new Reimbursement("60984f064cea0896f50fef46", "test1","title",
                4.0,"05-13-2021", "approved", "admin");
        reimbursementDao.addNewReimbursement(approvedReimbursement);
        ArrayList<Reimbursement> approvedResult = reimbursementDao.getReimbursementsOfEmployee(employee, "approved");

        Reimbursement declinedReimbursement = new Reimbursement("60984f064cea0896f50fef47", "test1","title",
                4.0,"05-13-2021", "declined", "admin");
        System.out.println(declinedReimbursement.getApprovedBy());

        reimbursementDao.addNewReimbursement(declinedReimbursement);
        ArrayList<Reimbursement> declinedResult = reimbursementDao.getReimbursementsOfEmployee(employee, "declined");
    }
    @Test
    void updateReimbursement() {
        boolean test = reimbursementDao.updateReimbursement(updateReimbursement);
        boolean test1 = reimbursementDao.updateReimbursement(updateReimbursement1);
    }

    @Test
    void getAllPrevReimbursements() {
//        reimbursementDao.getAllPrevReimbursements("tes");
//        reimbursementDao.getAllPrevReimbursements("*");
//        reimbursementDao.getAllPrevReimbursements("dav");
    }

    @Test
    void convert() {
        DBObject dbObject = reimbursementDao.convert(reimbursement);
    }


}