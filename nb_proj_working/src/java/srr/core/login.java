/**
 * A class to handle login verification and validation
 */
package srr.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.StudentAccount;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.*;

/**
 *
 * @author Jose Marte
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        String userName = request.getParameter("txtUserName");
        String psw = encodePassword(request.getParameter("txtPassword")); //hash the plain text psw

        String sql = "SELECT * FROM srr.student_account WHERE userName ='" + userName + "';";

        DbUtilities db = new DbUtilities();
        ResultSet rs;

        try {
            rs = db.getResultSet(sql);
            if (rs.next()) {
                //TODO: record count needs to be 1 for added security
                if (rs.getString("userName").equals(userName) && rs.getString("password").equals(psw)) {
                    //create StudentAccount instance and populate it from db to store in session
                    StudentAccount thisAccount = new StudentAccount(userName);
                    //put the student object in session
                    session.setAttribute("jsonStudent", thisAccount.getStudentAccountAsJSON());
                    session.setAttribute("userName", thisAccount.getUserName());
                    System.out.print("user logged in as: " + userName); //**********
                    response.sendRedirect("index.jsp");
                }
                else {
                    response.sendRedirect("login.jsp?error=true");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
