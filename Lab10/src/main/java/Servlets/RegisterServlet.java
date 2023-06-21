package Servlets;

import Models.User;
import Models.UserType;
import database.UsersDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        PrintWriter writer = response.getWriter();
        try {
            if(login.length() < 2 || password.length() < 2)
            {
                request.setAttribute("ErrorMessage","Wrong login or password!");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }

            UsersDAO usersDAO = new UsersDAO();

            if(usersDAO.GetUser(login) != null) {
                request.setAttribute("ErrorMessage","User with such login already exists");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
            else {
                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setRole(String.valueOf(Math.random() * 10 < 8 ? UserType.User : UserType.Admin));
                user.setRole("User");

                usersDAO.Register(login ,password, UserType.valueOf(user.getRole()));

                HttpSession session = request.getSession();
                session.setAttribute("userType", user.getRole());

                response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            }
        } catch(Exception exception) {
            request.setAttribute("ErrorCode", exception.fillInStackTrace());
            request.setAttribute("ErrorMessage", exception.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
