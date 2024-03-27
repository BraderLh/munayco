package com.pizzeria.munayco.aggregates.constants;

public class Constants {
    private Constants () {}

    public static final Integer CODE_SUCCESS=200;
    public static final Integer CODE_ERROR=400;

    public static final String MESS_SUCCESS="Execution successful";
    public static final String MESS_ERROR="Execution error: ";
    public static final String MESS_ERROR_DATA_NOT_VALID="Error: Invalid input data or missed data";
    public static final String MESS_NOT_FOUND_ID="Error: Id not exists or is not correct";
    public static final String MESS_ZERO_ROWS="Error: No rows or zero rows for this request";
    public static final String MESS_ERROR_NOT_DELETE = "Error: Fail deleting, something wrong happened";
    public static final String MESS_ERROR_NOT_FIND = "Error: Fail searching, something wrong happened";
    public static final String MESS_ERROR_NOT_UPDATE ="Error: Fail updating, something wrong happened";

    public static final String MESS_ERROR_LOGIN = "Error: Signing up failed";

    public static final String AUDIT_ADMIN = "Admin";
    public static final String AUDIT_USER = "User";
    public static final int STATUS_INACTIVE = 0;
    public static final int STATUS_ACTIVE = 1;
}
