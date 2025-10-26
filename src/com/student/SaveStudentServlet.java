package com.student;

import com.student.CassandraConnector;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;

public class SaveStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentNumber = request.getParameter("student_number");
        String name = request.getParameter("name");               
        String program = request.getParameter("program");
        String year = request.getParameter("year");

        try (CqlSession session = CassandraConnector.getSession()) {
     
            String query = "INSERT INTO students (student_number, name, program, year) VALUES (?, ?, ?, ?)";
            SimpleStatement stmt = SimpleStatement.newInstance(
                    query,
                    studentNumber,
                    name,
                    program,
                    Integer.parseInt(year)
            );
            session.execute(stmt);
            out.println("<h2>Student data saved successfully!</h2>");
        } catch (Exception e) {
            e.printStackTrace(out);
            out.println("<h2>Error saving student data: " + e.getMessage() + "</h2>");
        }
    }
}
