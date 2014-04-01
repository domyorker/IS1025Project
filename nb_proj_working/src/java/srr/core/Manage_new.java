package srr.core;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.StudentAccount;

/**
 * A servlet to manage the entry and modification of resumes. This is the brains
 * of the application.
 *
 * @author Jose Marte
 */
@WebServlet(name = "Manage", urlPatterns = {"/Manage"})
public class Manage_new extends HttpServlet {

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

        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            //confirm user is logged in and is of userType "student" (admins do not add resumes)
            HttpSession session = request.getSession(true);
            if (session.isNew() || session.getAttribute("userName") == null) {
                response.sendRedirect("login.jsp"); //redirect to login page
            }
            switch (action) {
                case "addResume":
                    out.println("Action: newResume<br/>");
                    //grab the name 
                    String name = request.getParameter("txtResumeName");
                    session.setAttribute("resumeID", "1"); //set the resume as current in session
                    session.setAttribute("resumeTitle", name);
                    //set all other session variables b4 redirecting to authoring page
                    response.sendRedirect("author.jsp");
                    break;
                case "addSkill":
                    out.print("Action: Add Skill");
                    break;
                case "editResume":
                    //get the resume's id
                    String resumeID = request.getParameter("resumeID");
                    if (resumeID != null && !resumeID.isEmpty()) {
                        //request data from resume object and put it in session
                        session.setAttribute("resumeID", resumeID);
                        session.setAttribute("resumeTitle", "The title obtained from DB");
                        //redirect to author[ing] page
                        response.sendRedirect("author.jsp");
                    }
                case "register":
//                    //build up the StudentAccount object...
//                    //..but validate data first...
//                    String errorMsgsList = ""; //track any errors while validating
//                    StudentAccount newAccount; //variable to store account in working memory
//
//                    //obtain the form data
//                    String userName = request.getParameter("txtUserName");
//                    String password = request.getParameter("txtPassword");
//                    if (!userName.isEmpty() && !password.isEmpty()) {
//                        //enforce a min of 6 char long userName...
//                        if (userName.length() < 6) {
//                            errorMsgsList = "<li>Please use six or more characters in your user name.</li>";
//                        }
//                    }
//                    String fName = request.getParameter("txtFirstName");
//                    if (fName.isEmpty()) {
//                        errorMsgsList += "<li>You must supply a First name.</li>";
//                    }
//                    String lName = request.getParameter("txtLastName");
//                    if (lName.isEmpty()) {
//                        errorMsgsList += "<li>You must supply a Last name.</li>";
//                    }
//                    String email = request.getParameter("txtEmail");
//                    //ensure email contains @pitt.edu 
//                    // if (!email.isEmpty()) {
//                    String temp[] = email.split("@");
//                    //username portion must be word + number lowercase
//                    if (!temp[0].matches("([a-z\\d]+)") || !temp[1].equals("pitt.edu")) { //does not validate
//                        errorMsgsList += "<li>Email " + email + " is invalid. Email must conform to user[#]@pitt.edu in lower case.</li>";
//                    }
//                    
//                    String addressLine1 = request.getParameter("txtAddressLine1");
//                    if (addressLine1.isEmpty()) {
//                        errorMsgsList += "<li>Warning: Did you forget to supply your address?</li>";
//                    }
//                    String addressLine2 = request.getParameter("txtAddressLine2");
//                    String stateID = request.getParameter("ddlState");
//                    String city = request.getParameter("txtCity");
//                    if (city.isEmpty()) {
//                        errorMsgsList += "<li>Please provide a city.</li>";
//                    }
//                    String ZIP = request.getParameter("txtZIP");
//                    if (ZIP.isEmpty()) {
//                        errorMsgsList += "<li>ZIP code is required.</li>";
//                    }
//                    String phone = request.getParameter("txtTel");
//                    String classLevelID = request.getParameter("ddlGradeLevel");
//                    //check for the existance of errors and redirect to error page if that's the case
//                    if (!errorMsgsList.isEmpty()) {
//                        session.setAttribute("registrationSuccess", "false");
//                        session.setAttribute("errorList", "<ul>" + errorMsgsList + "</ul>");
//                        response.sendRedirect("register.jsp?error=true"); //spit back errors
//                    } else {
//                        //instantiate the account object...
//                        newAccount = new StudentAccount(userName, password);
//                        newAccount.setFirstName(fName);
//                        newAccount.setLastName(lName);
//                        newAccount.setPhone(phone);
//                        newAccount.setEmail(email);
//                        newAccount.setAddressLine1(addressLine1);
//                        newAccount.setAddressLine2(addressLine2);
//                        newAccount.setCity(city);
//                        newAccount.setStateID(Integer.parseInt(stateID));
//                        newAccount.setClassLevelID(Integer.parseInt(classLevelID));
//                        newAccount.setZIP(ZIP);
//                        commitNewRegistration(newAccount);
//                        response.sendRedirect("login.jsp");
//                    }
//                    break;
                    //old code******************
                    String errorMsgsList = ""; //track any errors while validating
                    StudentAccount newAccount; //variable to store account in working memory
                    //obtain the form data
                    String userName = request.getParameter("txtUserName");
                    String password = request.getParameter("txtPassword");
                    if (!userName.isEmpty() && !password.isEmpty()) {
                        newAccount = new StudentAccount(userName, password);
                        if (newAccount.isExistingUser()) {
                            errorMsgsList = "<li>The user name you supplied is not available. Please try a different one.</li>";
                        }
                    }
                    String fName = request.getParameter("txtFirstName");
                    if (fName.isEmpty()) {
                        errorMsgsList += "<li>You must supply a First name.</li>";
                    }
                    String lName = request.getParameter("txtLastName");
                    if (lName.isEmpty()) {
                        errorMsgsList += "<li>You must supply a Last name.</li>";
                    }
                    String email = request.getParameter("txtEmail");
                    //ensure email contains @pitt.edu 
                    // if (!email.isEmpty()) {
                    String temp[] = email.split("@");
                    //username portion must be word + number lowercase
                    if (!temp[0].matches("([a-z\\d]+)") || !temp[1].equals("pitt.edu")) {
                        errorMsgsList += "<li>Email " + email + " is invalid. Email must conform to user123@pitt.edu in lower case.</li>";
                    }
                    //TODO: continue to validate all fields
                    String addressLine1 = request.getParameter("txtAddressLine1");
                    String addressLine2 = request.getParameter("txtAddressLine2");
                    String stateID = request.getParameter("ddlState");
                    String city = request.getParameter("txtCity");
                    String ZIP = request.getParameter("txtZIP");
                    String phone = request.getParameter("txtTel");
                    String classLevelID = request.getParameter("ddlGradeLevel");
                    //check for the existance of errors and redirect to error page if that's the case
                    if (!errorMsgsList.isEmpty()) {
                        //session.setAttribute("registrationSuccess", "false");
                        session.setAttribute("errorList", "<ul>" + errorMsgsList + "</ul>");
                      // response.sendRedirect("register.jsp?error=true"); //spit back errors
                    } else {                       
                        response.sendRedirect("login.jsp?msg=success");
                    }
                    break;
                default:
                    break;
            }
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

//    /**
//     * A method to commit the registration to the db
//     *
//     * @param account The account object to commit to database
//     */
//    public boolean commitNewRegistration(StudentAccount account) {
//
//        return (account.commit()); //saves account to db
//
//    }

}
