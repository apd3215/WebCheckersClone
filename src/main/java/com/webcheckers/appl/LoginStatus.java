package com.webcheckers.appl;

/**
 * Represents login statuses.
 * Numbers were integers that represented enum before change
 * TODO: Remove numbers
 * @author Joshua Yoder
 */
public enum LoginStatus {
    INVALID_USER_FORMAT, //0
    INVALID_PASS_FORMAT, //-1
    NEW_USER_LOGIN, //1
    EXISTING_USER_LOGIN, //2
    USER_ALREADY_LOGIN, //-2
    WRONG_PASS_OR_USER_EXISTS //3
}