package Servlets.FirstServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

@WebServlet(name = "cookie" , value = "/cookie")
public class CookieServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie sessionCookie = new Cookie("time", LocalTime.now().toString());
        Cookie visitCookie = new Cookie("visits", "2");
        Cookie userTypeCookie = new Cookie("userType", "admin");


        response.addCookie(sessionCookie);
        response.addCookie(visitCookie);
        response.addCookie(userTypeCookie);

        try (PrintWriter out = response.getWriter()) {
            out.println("From cookies:");
            out.println("Cookie time: " + sessionCookie.getValue());
            out.println("Cookie visits: " + visitCookie.getValue());
            out.println("Cookie userType: " + userTypeCookie.getValue());
        }

    }

}
