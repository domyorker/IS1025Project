package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;

/**
 * A class to represent a resume
 *
 * @author Jose Marte
 * @author stevenschilinski
 */
public class Resume {

    private BigInteger resumeID; //AUTO_INCREMENT
    private BigInteger userID;
    private String title;
    private String objective;
    private String experience;
    private String accomplishments;

    //list of related related objects
    private ArrayList<Certification> certificationsList;
    private ArrayList<Course> coursesList;
    private ArrayList<Education> educationList;
    private ArrayList<Interest> interestsList;
    private ArrayList<Membership> membershipsList;
    private ArrayList<Reference> referencesList;
    private ArrayList<Skill> skillsList;
    private DbUtilities db;
    ResultSet rs;

    /**
     * Constructor takes in an ID and a title; populates those fields
     *
     * @param userID
     * @param title
     */
    public Resume(BigInteger userID, String title) {
        this.userID = userID;
        this.title = title;
    }

    /**
     * Constructor takes in an ID and populates all fields from the database
     *
     * @param ID The record's id
     *
     */
    public Resume(BigInteger ID) {

        this.resumeID = ID;
        DbUtilities db = new DbUtilities();
        try {
            ResultSet rs = db.getResultSet("SELECT title FROM srr.student_resume WHERE resumeID ='" + resumeID + "'");
            while (rs.next()) {
                this.title = rs.getString("title");
            }

            rs = db.getResultSet("SELECT * FROM srr.summary WHERE resumeID ='"
                    + this.resumeID + "'");

            while (rs.next()) {
                String objective = rs.getString("objective");
                if (objective != null) {
                    this.objective = objective;
                }
                String experience = rs.getString("experience");

                if (experience != null) {
                    this.experience = experience;
                }
                String accomplishments = rs.getString("accomplishments");
                if (accomplishments != null) {
                    this.accomplishments = accomplishments;
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        }
        //start filling in related tables..
        this.certificationsList = getCertificationsFromDb(resumeID);

    }

    /**
     * A helper method to populate the Certification list
     *
     * @param resumeID The resume id of the certifications
     * @return A list of Certification objects for that particular resume
     */
    private ArrayList<Certification> getCertificationsFromDb(BigInteger resumeID) {

        db = new DbUtilities();
        ArrayList<Certification> tempList = new ArrayList<>();
        Certification tempCertification;

        String sql = "SELECT * FROM srr.resume_certification RC";
        sql += " LEFT JOIN srr.certification C ON RC.certificationID = C.certificationID";
        sql += "WHERE  resumeID =" + resumeID + ";";

        try {
            this.rs = db.getResultSet(sql);

            while (rs.next()) {
                tempCertification = new Certification(BigInteger.valueOf(rs.getLong("certificationID")));
                tempList.add(tempCertification);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentAccount.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return tempList;
    }

    /**
     * A helper method to populate the Course list
     *
     * @param resumeID The resume id of the courses
     * @return A list of Course objects for that particular resume
     */
    //TODO: continue to work on Course Class
    private ArrayList<Course> getCoursesFromDb(BigInteger resumeID) {

        db = new DbUtilities();
        ArrayList<Course> tempList = new ArrayList<>();
        Course tempCourse;

        String sql = "SELECT * FROM srr.resume_course RC";
        sql += " LEFT JOIN srr.course C ON RC.courseID = C.courseID";
        sql += "WHERE  resumeID =" + resumeID + ";";

        try {
            this.rs = db.getResultSet(sql);

            while (rs.next()) {
                //tempCourse = new Course(BigInteger.valueOf(rs.getLong("courseID")));
                // tempList.add(tempCourse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentAccount.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return tempList;
    }
}
