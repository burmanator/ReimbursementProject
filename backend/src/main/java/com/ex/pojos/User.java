package com.ex.pojos;

/**
 * This class is the plain old java object for a User.
 * It implements different getter and setter methods of the fields
 * of this object. Employee and Manager extend from this class.
 */
public abstract class User {
    protected String username;
    protected String password;
    protected String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
