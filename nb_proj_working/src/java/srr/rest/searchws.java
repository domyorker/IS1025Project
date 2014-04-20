
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
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

@WebServlet(urlPatterns = {"/searchws"})
public class searchws extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession();
            String programLangInput = request.getParameter("programLang");
            String skill = request.getParameter("skill");
            String keyWord = request.getParameter("key");
            //keyWord ="math1";
            if (skill != null) {
                out.print("Skill is +  " + skill);
                DbUtilities db = new DbUtilities();
                try {
                    ResultSet set1 = db.getResultSet("select * from srr.skill join srr.resume_skill on srr.resume_skill.skillID = srr.skill.skillID  join\n "
                            + "srr.student_resume on srr.student_resume.resumeID = srr.resume_skill.resumeID join srr.student_account on\n "
                            + "srr.student_account.studentID = srr.student_resume.userID where \n"
                            + "                            match (name) against ('" + skill + "');");

                    HashSet matchedResults = new HashSet();
                    if (set1 == null) {
                        response.sendRedirect("search.jsp?error=noMathedResults");
                    }
                    while (set1.next()) {
                        out.print("The student Id is + " + set1.getString("StudentID"));
                        StudentAccount tmpStudent = new StudentAccount(BigInteger.valueOf(set1.getLong("studentID")));
                        System.out.println(tmpStudent.getAddressLine1());
                        System.out.println(tmpStudent.getFirstName());
                        matchedResults.add(set1.getString(1));
                        out.println(set1.getString(1));
                        session.setAttribute("matchedResults", matchedResults);

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(searchws.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    db.releaseConnection();
                }

            } else if (keyWord != null) {
                String sql
                        = " SELECT distinct userID FROM srr.student_resume left join "
                        + "  srr.resume_membership on srr.resume_membership.resumeID = srr.student_resume.resumeID left join  "
                        + "  srr.membership on srr.membership.membershipID = srr.resume_membership.membershipID left join srr.resume_award "
                        + "  on srr.resume_award.resumeID = srr.student_resume.resumeID left join srr.award on "
                        + "  srr.award.awardID = srr.resume_award.awardID left join srr.resume_skill on "
                        + "  srr.resume_skill.resumeID = srr.student_resume.resumeID left join srr.skill on "
                        + "  srr.resume_skill.skillID = srr.skill.skillID left join srr.resume_course on "
                        + "  srr.student_resume.resumeID = srr.resume_course.resumeID left join srr.course on "
                        + "  srr.resume_course.courseID = srr.course.courseID  left join srr.resume_certification on  "
                        + "  srr.resume_certification.resumeID = srr.student_resume.resumeID left join srr.certification on  "
                        + "  srr.certification.certificationID = srr.resume_certification.certificationID left join srr.reference on "
                        + "  srr.reference.resumeID = srr.student_resume.resumeID left join srr.summary on "
                        + "  srr.summary.resumeID = srr.student_resume.resumeID  left join srr.resume_experience on srr.student_resume.resumeID = srr.resume_experience.resumeID  left join "
                        + "  srr.experience on srr.experience.experienceID = srr.resume_experience.experienceID "
                        + "  where srr.student_resume.resumeID ='0' or  "
                        + "  srr.membership.membershipName ='" + keyWord + "' or srr.award.name ='" + keyWord + "'  or  srr.skill.name = '" + keyWord + "' or "
                        + "  srr.course.name = '" + keyWord + "' or srr.certification.name = '" + keyWord + "' or srr.certification.institution = '" + keyWord + "'  or "
                        + "  srr.summary.accomplishments = '" + keyWord + "'  or match ( srr.certification.summary) against('" + keyWord + "') or "
                        + "  match ( srr.summary.accomplishments) against('" + keyWord + "') or match ( srr.summary.objective) against('" + keyWord + "')"
                        + "  or match ( srr.summary.experience) against('" + keyWord + "') or match ( srr.experience.summary) against('" + keyWord + "');";

                DbUtilities db = new DbUtilities();
                ResultSet set1;
                try {
                    set1 = db.getResultSet(sql);
                    HashSet matchedResults = new HashSet();
                    if (set1 == null) {
                        response.sendRedirect("search.jsp?error=noMathedResults");
                    }
                    while (set1.next()) {
                        matchedResults.add(set1.getString(1));
                        out.println(set1.getString(1));
                    }

                    session.setAttribute("matchedResults", matchedResults);

                } catch (SQLException ex) {
                    Logger.getLogger(searchws.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    db.releaseConnection();
                }

            } else if (programLangInput != null) {
                DbUtilities db = new DbUtilities();
                String sql = " Select userID from srr.student_resume join srr.resume_skill on srr.student_resume.resumeID = srr.resume_skill.resumeID join  "
                        + "  srr.skill on srr.skill.skillID = srr.resume_skill.skillID where  srr.skill.name ='" + programLangInput + "'";

                ResultSet set1 = db.getResultSet(sql);

                HashSet matchedResults = new HashSet();
                if (set1 == null) {
                    response.sendRedirect(response.encodeRedirectURL("search.jsp?error=true"));
                    session.setAttribute("errorList", "<ul><li>No records match your query.</li></ul");
                    return;
                }
                while (set1.next()) {
                    matchedResults.add(set1.getString(1));
                    out.println(set1.getString(1));
                }

                session.setAttribute("matchedResults", matchedResults);

                db.releaseConnection();
            } else {
                response.sendRedirect(response.encodeRedirectURL("search.jsp?error=true"));
                session.setAttribute("errorList", "<ul><li>No records match your query.</li></ul");
                return;
            }
            //take if u want to test!
            response.sendRedirect("search.jsp");

        } catch (SQLException ex) {
            Logger.getLogger(searchws.class.getName()).log(Level.SEVERE, null, ex);
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
