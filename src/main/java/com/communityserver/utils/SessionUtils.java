package com.communityserver.utils;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    private SessionUtils() {
    }

    public static void setLoginUserNumber(HttpSession session, int userNumber) {
        session.setAttribute(LOGIN_MEMBER_ID , userNumber);
    }

    public static void setAdminLoginUserNumber(HttpSession session, int userNumber) {
        session.setAttribute(LOGIN_ADMIN_ID, userNumber);
    }

    public static Integer getLoginUserNumber(HttpSession session) {
        return (Integer) session.getAttribute(LOGIN_MEMBER_ID);
    }

    public static Integer getAdminLoginUserNumber(HttpSession session) {
        return (Integer) session.getAttribute(LOGIN_ADMIN_ID);
    }

    public static void clear(HttpSession session){
        session.invalidate();
    }
}
