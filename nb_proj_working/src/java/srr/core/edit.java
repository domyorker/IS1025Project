package srr.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
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
            }

//Now for the action...
            String action = request.getParameter("action");
            switch (action) {
                case "create": //request to create a new resume 
                    //grab resume title and student studentAccount
                    String title = request.getParameter("txtResumeName");
                    studentAccount = (StudentAccount) session.getAttribute("StudentAccount");
                    if (title.isEmpty()) {
                        errorList += "<li>Please enter a title for the resume.</li>";
                        redir = response.encodeRedirectURL("manage.jsp?error=true");
                    }

                    if (errorList.isEmpty()) { //NO errors present
                        try {
                            resume = new Resume(studentAccount.getStudentID(), title); //create the resume
                            session.setAttribute("Resume", resume); //put it in session
                            //now save
                            resume.createToDb(studentAccount);
                            redir = response.encodeRedirectURL("edit.jsp");
                        } catch (NullPointerException ex) {
                            errorList += "<li>An error occured while attempting to create your resume. Please try again.</li>";
                            redir = response.encodeRedirectURL("manage.jsp?error=true");
                            System.out.println("error in create (edit.java) while creating resume: " + ex.getMessage());
                        }

                    }
                    if (!errorList.isEmpty()) { //errors present
                        session.setAttribute("errorList", "<ul>" + errorList + "</ul>");
                    } else {//important...put the new object in session
                        session.setAttribute("Resume", resume);
                        success = true; //important
                    }
                    break;
                case "save": //save the resume
                    if (resume == null) {
                        break; //exit
                    }

                    tempResume = new Resume(request.getParameter("txtResumeName")); //(studentAccount.getStudentID(), );
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

                    //************** Grab the work experience
                    Experience tempExperience;
                    String employer,
                     jobTitle,
                     startDate,
                     endDate = "",
                     jobSummary;
                    int presentJob = 0;

                    for (int i = 1; i <= 5; i++) {
                        jobTitle = request.getParameter("txtJobTitle" + i);
                        employer = request.getParameter("txtEmployer" + i);
                        startDate = request.getParameter("txtStartDate" + i);
                        jobSummary = request.getParameter("txtJobSummary" + i);

                        if (request.getParameter("radioPresentJob" + i).equals("Yes")) {
                            presentJob = 1;
                        } else {
                            endDate = request.getParameter("txtEndDate" + i);
                        }

                        if (!jobTitle.isEmpty()) {
                            tempExperience = new Experience(jobTitle, employer, startDate, presentJob, endDate, jobSummary);
                            tempResume.addExperience(tempExperience);
                        } else {
                            break;
                        }
                    } //end for each experience
                    //put back in session 
                    session.setAttribute("Resume", tempResume); //time to save our work
                    //create for the first time?
                    if (resume.getResumeID() == null) {
                        success = tempResume.createToDb(studentAccount);

                    } else { //existing
                        //important: delete all records related to this resume...we are starting fresh...
                        resume.clearDBRelations();

                        tempResume.setResumeID(resume.getResumeID()); //swap the ID before updating

                        success = tempResume.update();
                    }//end !resume.isCommited()

                    break;

                case "edit":
                    String theID = request.getParameter("resumeID");
                    if (theID != null && !theID.isEmpty()) {

                        tempResume = new Resume(new BigInteger(theID));
                        if (tempResume != null) {
                            session.setAttribute("Resume", tempResume);
                            success = true;
                            redir = response.encodeRedirectURL("edit.jsp");
                        } else {
                            errorList = "<li>An error occured while trying to grab resume with ID" + theID + ".</li>";
                            redir = response.encodeRedirectURL("edit.jsp?error=true");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (NullPointerException ex) {
            System.out.println("Error in edit.java: " + ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
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
