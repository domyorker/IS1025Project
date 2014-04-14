package srr.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import srr.model.Experience;
import srr.model.Resume;
import srr.model.Skill;
import srr.model.StudentAccount;

/**
 * A servlet to manage the modification of resumes.
 *
 * @edit Jose Marte
 */
@WebServlet(name = "edit", urlPatterns = {"/edit"})
public class edit extends HttpServlet {

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
        String redir = response.encodeRedirectURL("edit.jsp?success=true");

        HttpSession session = request.getSession(true);
        StudentAccount studentAccount = (StudentAccount) session.getAttribute("StudentAccount");
        Resume resume;
        Resume tempResume;

        String errorList = "";
        String successMsg = "";
        boolean success = false;
        try {
            resume = (Resume) session.getAttribute("Resume");
            if (session.isNew()) {
                redir = response.encodeRedirectURL("login.jsp?error=true");
                errorList = "<li>You must log in to edit resumes. Please log in.</li>";
            } else if (resume == null) {
                redir = response.encodeRedirectURL("manage.jsp?error=true");
                errorList = "<li>A resume was not found. Please select or create the resume you wish to work with.</li>";
            }

//Now for the action...
            String action = request.getParameter("action");
            switch (action) {
                case "save": //save the resume
                    if (resume == null) {
                        break; //exit
                    }

                    tempResume = new Resume(studentAccount.getStudentID(), request.getParameter("txtResumeName"));
                    System.out.println("THE TITLE: " + request.getParameter("txtResumeName"));
                    tempResume.setObjective(request.getParameter("txtObjective"));
                    tempResume.setExperience(request.getParameter("txtExperience"));
                    tempResume.setAccomplishments(request.getParameter("txtAccomplishments"));

                    //grab the skills   
                    ArrayList<Skill> newSkillList = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        String skillName = request.getParameter("txtSkill_" + i);
                        if (!skillName.isEmpty()) {
                            newSkillList.add(new Skill(skillName)); //add a new skill

                        }
                        tempResume.setSkillsList(newSkillList);
                    } //end for

                    //grab the work experience
                    Experience tempExperience;
                    String employer;
                    String jobTitle;
                    Date startDate;
                    boolean presentJob;
                    Date endDate;
                    String summary;
                    for (int i = 1; i <= 5; i++) {
                        if (!request.getParameter("txtJobTitle" + i).isEmpty()) {
                            employer = request.getParameter("txtJobTitle" + i);
                            System.out.println("txtStartDate: " + i + request.getParameter("txtStartDate" + i));
                            tempExperience = new Experience();
                            tempResume.addExperience(new Experience());
                        } else {
                            break;
                        }
                    } //end for each experience
                    //put back in session 
                    session.setAttribute("Resume", tempResume); //time to save our work
                    //create for the first time?
                    if (resume.getResumeID() == null) {
                        success = tempResume.commitToDb(studentAccount);
                    } else { //existing
                        //important: delete all records related to this resume...we are starting fresh...
                        resume.clearDBRelations();
                        tempResume.setResumeID(resume.getResumeID()); //swap the ID before updating
                        success = tempResume.update();
                    }//end !resume.isCommited()

                    break;

                case "edit":
                    String theID = request.getParameter("resumeID");
                    System.out.print("Edit.java the ID: " + theID);
                    if (theID != null && !theID.isEmpty()) {

                        tempResume = new Resume(new BigInteger(theID));
                        if (tempResume != null) {
                            session.setAttribute("Resume", tempResume);
                            success = true;

                            redir = response.encodeRedirectURL("edit.jsp");
                        } else {
                            errorList = "<li>An error occured while trying to grab resume with ID" + theID + ".</li>";
                            redir = response.encodeRedirectURL("edit.jsp?error=true");
                            System.out.println("error in edit");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (NullPointerException ex) {
            System.out.println("Error in edit.jsp: " + ex.getMessage());
        }
        if (!success) {
            errorList = "<li>An error has occured while attempting to save your resume. Please try again.</li>";
        } else {
            successMsg = "<li>Your resume has been saved.</li>";
        }
        //errors!
        if (!errorList.isEmpty()) { //errors present
            session.setAttribute("errorList", "<ul>" + errorList + "</ul>");
        }
        if (!successMsg.isEmpty()) {
            session.setAttribute("successMsg", "<ul>" + successMsg + "</ul>");
        }

        System.out.println("redir:  " + redir);
        response.sendRedirect(redir);

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
