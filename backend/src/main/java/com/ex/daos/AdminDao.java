package com.ex.daos;

import com.ex.pojos.Admin;
import com.ex.pojos.Employee;
import com.mongodb.*;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class provide the interaction between the Object Admin
 * and the MongoDatabase. This class implements validation, adding
 * new admin, and finding a new admin.
 */
public class AdminDao {
    private MongoClient mongoClient;
    private DBCollection admins;
    Logger logger = LogManager.getLogger(EmployeeDao.class);

    public AdminDao() throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB("Project1");
        admins = database.getCollection("admins");
    }
    public AdminDao(String name) throws UnknownHostException {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        DB database = mongoClient.getDB(name);
        admins = database.getCollection("admins");
    }

    /**
     * This verifies the provided username and password do exist in the
     * database.
     *
     * @param username from which the session user is attempting to login
     * @param password from which the session user is attempting to login
     * @return true if parameters are validated by the database
     */
    public boolean validateAdmin(String username, String password){
        DBObject query = new BasicDBObject("username", username).append("password", password);
        DBObject Admin = admins.findOne(query);
        if(Admin!=null){
            logger.info(username+" successfully logged in");
            return true;
        }
        else {
            logger.info(username+" failed to log in");
            return false;
        }
    }

    /**
     * This method adds a new admin into the databse
     * @param Admin object who shall be added to the database
     * @return true if the addition of the new admin is successful
     */
    public boolean addAdmin(Admin Admin){
        DBObject user = new BasicDBObject("username",Admin.getUsername())
                .append("password", Admin.getPassword())
                .append("email", Admin.getEmail());
        admins.insert(user);
        logger.info("A new account was created for "+Admin.getUsername());
        return true;
    }

    /**
     * This method attempts to find an admin with the
     * given username.
     * @param username of an admin whose existence in the database will be verified
     * @return true if the admin is found
     */
    public Admin findAdmin(String username){
        DBObject user = new BasicDBObject("username",username);
        DBCursor cursor = admins.find(user);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String adminUsername = obj.get("username").toString();
            String adminEmail= obj.get("email").toString();
            return new Admin(adminUsername,adminEmail);

        }
        return null;
    }

    /**
     * This method will find the existing employee and shall update the username of the Employee
     * @param admin who's username shall be updated
     * @param newUsername is the new username that will be assigned to the Employee
     * @return true if successful
     */
    public boolean updateAdminUsername(Admin admin, String newUsername) {
        DBObject query = new BasicDBObject("username", admin.getUsername());
        DBCursor cursor = admins.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password = obj.get("password").toString();
            admins.remove(query);
            Admin updatedUsername = new Admin(newUsername,admin.getEmail(),password);
            boolean add = addAdmin(updatedUsername);
            if(add){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is in charge of updating the admin's email if the admin exists
     * in the system
     * @param admin the email of whom to update
     * @param newEmail the new email to update to
     * @return true if successful, false otherwise
     */
    public boolean updateAdminEmail(Admin admin, String newEmail) {
        DBObject query = new BasicDBObject("username", admin.getUsername());
        DBCursor cursor = admins.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password = obj.get("password").toString();
            admins.remove(query);
            Admin updatedEmail = new Admin(admin.getUsername(),password,newEmail);
            boolean add = addAdmin(updatedEmail);
            System.out.println(add);
            return true;
        }
        return false;
    }
    /**
     * This method is in charge of updating the admin's password if the admin exists
     * in the system and if the old password is authenticated
     * @param admin the email of whom to update
     * @param oldPassword the old password to authenticate with
     * @param newPassword1 the new password to update to
     * @return true if successful, false otherwise
     */
    public boolean updateAdminPassword(Admin admin, String oldPassword, String newPassword1) {
        DBObject query = new BasicDBObject("username", admin.getUsername());
        DBCursor cursor = admins.find(query);
        if(cursor.hasNext()){
            DBObject obj = cursor.next();
            String password =obj.get("password").toString();
            //password entered is not correct
            if(!oldPassword.equals(password)){
                return false;
            }
            String email = obj.get("email").toString();
            admins.remove(query);
            Admin updatedPassword = new Admin(admin.getUsername(),newPassword1,email);
            boolean add = addAdmin(updatedPassword);
            System.out.println(add);
            return true;
        }
        return false;
    }

    /**
     * This method gets the admin's password, given the username.
     * This method is called sparingly to only validate if absolutely
     * necessary to validate
     * @param username of whom we would like to retrieve the password
     * @return the password of the admin
     */
    public String getPassword(String username) {
        DBObject query = new BasicDBObject("username", username);
        DBObject obj= admins.findOne(query);
        return obj.get("password").toString();
    }
}
