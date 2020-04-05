package com.webcheckers.appl;

/**
 * Represents login statuses.
 * @author Joshua Yoder
 */
public enum LoginStatus {
    INVALID_USER_FORMAT, 
    INVALID_PASS_FORMAT, 
    NEW_USER_LOGIN,
    EXISTING_USER_LOGIN, 
    USER_ALREADY_LOGIN,
    WRONG_PASS_OR_USER_EXISTS
}