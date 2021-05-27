package com.ex.pojos;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.*;

/**
 * This class is the plain old java object for a Reimbursement.
 * It implements different getter and setter methods of the fields
 * of this object.
 */
public class Reimbursement {
    private String username;
    private String title;
    private Double amount;
    private String date;
    private String approved;
    private String id;
    private String approvedBy;

    public Reimbursement() {
    }

    public Reimbursement(String id, String user, String title, Double amount, String date, String approved) {
        this.username = user;
        this.amount = amount;
        this.date = date;
        this.approved = approved;
        this.title = title;
        this.id = id;

    }

    @JsonProperty("approvedBy")
    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Reimbursement( String id, String username, String title, Double amount, String date,
                          String approved, String approvedBy) {
        this.username = username;
        this.amount = amount;
        this.date = date;
        this.approved = approved;
        this.title = title;
        this.id = id;
        this.approvedBy = approvedBy;
    }

    public Reimbursement(String username, String title, Double amount, String date, String approved) {
        this.username = username;
        this.amount = amount;
        this.date = date;
        this.approved = approved;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.toString();
    }

    @JsonProperty("approved")
    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
