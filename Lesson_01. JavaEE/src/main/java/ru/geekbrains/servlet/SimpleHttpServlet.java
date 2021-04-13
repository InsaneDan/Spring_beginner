package ru.geekbrains.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = {"/http-servlet/*"})
public class SimpleHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // добавление аттрибутов и их значений
        req.setAttribute("pageHeader", "Основные методы HttpServlet");              // текст заголовка
        req.setAttribute("pageHeaderLvl", "h2");                                    // уровень заголовка
        // передать данные в другой сервлет и добавить ответ к текущей странице
        getServletContext().getRequestDispatcher("/page_header").include(req, resp);
        // основные методы HttpServlet
        resp.getWriter().println("<p>contextPath: " + req.getContextPath() + "</p>");
        resp.getWriter().println("<p>servletPath: " + req.getServletPath() + "</p>");
        resp.getWriter().println("<p>pathInfo: " + req.getPathInfo() + "</p>");
        resp.getWriter().println("<p>queryString: " + req.getQueryString() + "</p>");
        resp.getWriter().println("<p>param1: " + req.getParameter("param1") + "</p>");
        resp.getWriter().println("<p>param2: " + req.getParameter("param2") + "</p>");
        resp.getWriter().println("<p>User-Agent header: " + req.getHeader("User-Agent") + "</p>");
        // данные сессии
        HttpSession session = req.getSession(true);
        Integer counter = (Integer) session.getAttribute("counter");
        if (counter == null) {
            counter = 1;
        } else {
            counter++;
        }
        session.setAttribute("counter", counter);
        resp.getWriter().println("<p>Счетчик запросов в сессии " + counter + "</p>");
        resp.getWriter().println("<hr/>");
    }
}
