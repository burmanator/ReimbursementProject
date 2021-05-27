package com.ex.pojos;

/**
 * This class is the plain old java object for an Admin.
 * It implements different getter and setter methods of the fields
 * of this object.
 */
public class Admin extends User{
    private String username;
    private String password;
    private String email;
    public Admin() {
    }

    public Admin(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email= email;
    }

    public Admin(String username, String email) {
        this.username = username;
        this.email= email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
