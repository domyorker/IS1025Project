package srr.core;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.Resume;
import srr.model.Skill;
import srr.model.StudentAccount;

/**
 * A servlet to manage the entry and modification of resumes.
 *
 * @author Jose Marte
 */
@WebServlet(name = "author", urlPatterns = {"/author"})
public class author extends HttpServlet {

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

        try {
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                response.sendRedirect(response.encodeRedirectURL("author.jsp"));
            }

            String title = "";
            String errorMsg = "";
            String successMsg = "";
            String redir = "author.jsp"; //default redirect
            StudentAccount studentAccount = (StudentAccount) session.getAttribute("StudentAccount");
            Resume resume = null;
            //confirm user is logged in and is of userType "student" (admins do not add resumes)
            if (studentAccount == null) {
                errorMsg = "<li>Oops! Please log in.</li>";
                redir = response.encodeRedirectURL("login.jsp?error=true");
                response.sendRedirect(redir);
            }

            switch (action) {
                case "create": //request to create a new resume 
                    //grab resume title and student studentAccount
                    title = request.getParameter("txtResumeName");

                    studentAccount = (StudentAccount) session.getAttribute("StudentAccount");
                    if (title.isEmpty()) {
                        errorMsg += "<li>Please enter a title for the resume.</li>";
                        redir = response.encodeRedirectURL("manage.jsp?error=true");
                    }

                    if (errorMsg.isEmpty()) { //NO errors present
                        try {
                            resume = new Resume(studentAccount.getStudentID(), title); //create the resume
                            session.setAttribute("Resume", resume); //put it in session
                            redir = response.encodeRedirectURL("edit.jsp");
                        } catch (NullPointerException ex) {
                            errorMsg += "<li>An error occured while attempting to create your resume. Please try again.</li>";
                            redir = response.encodeRedirectURL("manage.jsp?error=true");
                            System.out.println("error in create (author.java) while creating resume: " + ex.getMessage());
                        }

                    }
                    if (!errorMsg.isEmpty()) { //errors present
                        session.setAttribute("errorList", "<ul>" + errorMsg + "</ul>");
                    } else {//important...put the new object in session
                        session.setAttribute("Resume", resume);
                    }
                    response.sendRedirect(redir);
                    break;
            }
////
//               
//                case "edit":
//                    //get the resume's id
//                    String resumeID = request.getParameter("resumeID");
//                    if (resumeID != null && !resumeID.isEmpty()) {
//                        //request data from resume object and put it in session
//                        resume = new Resume();
//                        resume.loadFromString(resumeID);
//                        session.setAttribute("Resume", resume);
//                    }
//
//                    //redirect to author[ing] page
//                    redir = response.encodeRedirectURL("author.jsp");
//                    response.sendRedirect(redir);
//                default:
//                    break;

        } catch (IOException ex) {
            System.out.println("exception in author.java" + ex.getMessage());
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
