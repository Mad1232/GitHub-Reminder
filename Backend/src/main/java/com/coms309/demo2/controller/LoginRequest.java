package com.coms309.demo2.controller;
/**
 * @author Fury Poudel and Madeleine Carydis
 * Represents login attempt
 */
public class LoginRequest {

    private String email;
    private String password;

    /**
     * Default constructor
     */
    public LoginRequest() {
    }

    // Constructor with parameters
    /**
     * Constructs a login request
     * @param email attempted email
     * @param password attempted password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    /**
     * Get email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
