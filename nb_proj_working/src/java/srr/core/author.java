package srr.core;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.Resume;
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
        if (session.isNew() || session.getAttribute("StudentAccount") == null) {
            response.sendRedirect("login.jsp"); //redirect to login page
        }
        try {
            String action = request.getParameter("action");
            //confirm user is logged in and is of userType "student" (admins do not add resumes)
            StudentAccount thisAccount = (StudentAccount) session.getAttribute("StudentAccount");
            switch (action) {
                case "create": //request to create a new resume (in memory)
                    String title = "";
                    String errorMsg = "";
                    String redir = "author.jsp";

                    if (session.getAttribute("userName") == null) {
                        errorMsg = "<li>Please login first, then try creating your resume.</li>";
                        redir = response.encodeRedirectURL("login.jsp?error=true");
                    } else {              //grab the name from the form
                        title = request.getParameter("txtResumeName");
                        if (title.isEmpty()) {
                            errorMsg += "<li>Please enter a title for the resume.</li>";
                            redir = response.encodeRedirectURL("manage.jsp?error=true");
                        }
                    }
                    if (!errorMsg.isEmpty()) { //errors present
                        session.setAttribute("errorList", "<ul>" + errorMsg + "</ul>");
                        response.sendRedirect(redir);
                    } else { //no errors
                        try {
                            Resume thisResume = new Resume(thisAccount.getStudentID(), title);
                            // if (thisResume.commitToDb(thisAccount)) {                    
                            session.setAttribute("Resume", thisResume);
                            //set all other session variables b4 redirecting to authoring page
                            response.sendRedirect(redir);
                        } catch (NullPointerException ex) {
                            //response.sendRedirect("login.jsp");
                            System.out.println("* * * ** * * * * * * * *Error in author create**");
                        }
                    }
                    break;
                case "commit":
                    System.out.print("Action: commit **************");
                    System.out.print("<h1>Scratch the surface, notice the gold buried underneath.");
                    session.setAttribute("successList", "<ul><li>Resume saved successfully.</li></ul>");
                    response.sendRedirect(response.encodeRedirectURL("manage.jsp"));
                    break;
                case "editResume":
                    //get the resume's id
                    String resumeID = request.getParameter("resumeID");
                    if (resumeID != null && !resumeID.isEmpty()) {
                        //request data from resume object and put it in session
                        //session.setAttribute("resumeID", resumeID);
                        // session.setAttribute("resumeTitle", "The title obtained from DB");
                        //redirect to author[ing] page
                        Resume thisResume = new Resume(resumeID);
                        response.sendRedirect("author.jsp");
                    }
                default:
                    break;
            }
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
