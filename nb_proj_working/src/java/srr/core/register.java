package srr.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.StudentAccount;

/**
 *
 * @author Jose Marte
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);

        String errorMsgsList = ""; //track any errors while validating
        StudentAccount newAccount; //variable to store account in working memory
        //obtain the form data
        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");

        newAccount = new StudentAccount(userName, password); //create the account object
        if (userName.isEmpty() || password.isEmpty()) {
            errorMsgsList += "<li>The user name and password fields are required.";
        } else if (newAccount.isExistingUser()) {
            errorMsgsList = "<li>The user name you supplied is not available. Please try a different one.</li>";
        }
        String fName = request.getParameter("txtFirstName");
        if (fName.isEmpty()) {
            errorMsgsList += "<li>You must supply a First name.</li>";
        } else {
            newAccount.setFirstName(fName);
        }
        String lName = request.getParameter("txtLastName");
        if (lName.isEmpty()) {
            errorMsgsList += "<li>You must supply a Last name.</li>";
        } else {
            newAccount.setLastName(lName);
        }
        String email = request.getParameter("txtEmail");
        //ensure email contains @pitt.edu 
        if (email.isEmpty()) {
            errorMsgsList += "<li>You must supply a valid Pitt email.</li>";
        } else {
            String temp[] = email.split("@");
            //username portion must be word + number lowercase
            if (!temp[0].matches("([a-z\\d]+)") || !temp[1].equals("pitt.edu")) {
                errorMsgsList += "<li>Email " + email + " is invalid. Email must conform to user123@pitt.edu in lower case.</li>";
            }
        }
        String addressLine1 = request.getParameter("txtAddressLine1");
        if (addressLine1.isEmpty() && !errorMsgsList.isEmpty()) {
            errorMsgsList += "<li>It is recommended you provide an address that will show on you resumes.</li>";
        }
        String addressLine2 = request.getParameter("txtAddressLine2");
        String stateID = request.getParameter("ddlState");
        if (stateID.isEmpty()) {
            errorMsgsList += "<li>You must select the state of your permanent address.</li>";
        }
        String city = request.getParameter("txtCity");
        if (city.isEmpty() && !errorMsgsList.isEmpty()) {
            errorMsgsList += "<li>Are you forgetting to provide a city?</li>";
        }
        String ZIP = request.getParameter("txtZIP");
        if (ZIP.isEmpty()) {
            errorMsgsList += "<li>ZIP code is required.</li>";
        }
        String phone = request.getParameter("txtTel");
        String classLevelID = request.getParameter("ddlGradeLevel");

        //check for the existance of errors and redirect to error page if that's the case
        if (!errorMsgsList.isEmpty()) {
            session.setAttribute("errorMsg", "<ul>" + errorMsgsList + "</ul>");
            response.sendRedirect("register.jsp?error=true"); //spit back errors
        } else {
            //valid...set the values
            newAccount.setFirstName(fName);
            newAccount.setLastName(lName);
            newAccount.setPhone(phone);
            newAccount.setEmail(email);
            newAccount.setAddressLine1(addressLine1);
            newAccount.setAddressLine2(addressLine2);
            newAccount.setCity(city);
            newAccount.setStateID(Integer.parseInt(stateID));
            newAccount.setClassLevelID(Integer.parseInt(classLevelID));
            newAccount.setZIP(ZIP);
            //save to db
            newAccount.commit();
            session.setAttribute("successMsg", "You have been successfully registered. Please proceed to logging in.");
            response.sendRedirect("login.jsp?success=true");
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
