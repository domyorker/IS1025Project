/**
 * A class to handle login verification and validation
 */
package srr.core;

import java.io.IOException;
import java.math.BigInteger;
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
        session.setMaxInactiveInterval(8000);

        String userName = request.getParameter("txtUserName");
        String psw = request.getParameter("txtPassword");
        boolean errorsPresent = false;
        String errorMsg = "<ul><li>Invalid credentials. Please try again.</li></ul>";

        psw = encodePassword(psw); //encode the password
        String sql = "SELECT * FROM srr.student_account WHERE userName ='" + userName + "' AND password ='" + psw + "';";

        DbUtilities db = new DbUtilities();
        ResultSet rs;

        try {
            rs = db.getResultSet(sql);
            if (rs.next()) {
                //create StudentAccount instance and populate it from db to store in session
                StudentAccount thisAccount = new StudentAccount(BigInteger.valueOf(rs.getLong("studentID")));
                thisAccount.setFirstName(rs.getString("fName"));
                thisAccount.setLastName(rs.getString("lName"));
                thisAccount.setEmail(rs.getString("email"));
                thisAccount.setPhone(rs.getString("phone"));
                thisAccount.setClassLevelID(rs.getInt("classLevelID"));
                thisAccount.setAddressLine1(rs.getString("addressLine1"));
                thisAccount.setAddressLine2(rs.getString("addressLine2"));
                thisAccount.setCity(rs.getString("city"));
                thisAccount.setStateID(rs.getInt("stateID"));
                thisAccount.setZIP(rs.getString("ZIP"));

                //put the student object in session
                session.setAttribute("StudentAccount", thisAccount);
            } else { //login not found
                errorsPresent = true;
            }
            if (!errorsPresent) {
                String redir = response.encodeRedirectURL("index.jsp");
                response.sendRedirect(redir);
            } else {
                session.setAttribute("errorList", errorMsg);
                String redir = response.encodeRedirectURL("login.jsp?error=true");
                response.sendRedirect(redir);
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
