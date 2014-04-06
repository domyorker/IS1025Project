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
    private ResultSet rs;
    
    public Resume(){
        
    }

    /**
     * Constructor takes in a title to instantiate this object
     *
     * @param title
     */
    public Resume(String title) {
        this.userID = null;
        this.title = title;
    }

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
     * @param ID The record's id as a String
     *
     */
    public void loadResume(String ID) {

        try {
            this.setResumeID(new BigInteger(ID.getBytes())); //gettting a String, so convert it to bigInt
        } catch (Exception ex) {
            System.out.println("An error has occured in loadResume while converting String to BigInteger." + ex.getMessage());
        }
        db = new DbUtilities();
        try {
            rs = db.getResultSet("SELECT title FROM srr.student_resume WHERE resumeID ='" + ID + "'");
            //set the title
            while (rs.next()) {
                this.setTitle(rs.getString("title"));
            }

            rs = db.getResultSet("SELECT * FROM srr.summary WHERE resumeID ='"
                    + this.getResumeID() + "'");
            if (rs.next()) {
                this.setObjective(rs.getString("objective"));
                this.setExperience(rs.getString("experience"));
                this.setAccomplishments(rs.getString("accomplishments"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Resume.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.releaseConnection();
        }
        //start filling in related tables..
       // this.certificationsList = getCertificationsFromDb(this.resumeID);

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

    public boolean commitToDb(StudentAccount account) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * A getter method to obtain the resume's ID
     * @return the resumeID
     */
    public BigInteger getResumeID() {
        return this.resumeID;
    }

    /**
     * @param resumeID the resumeID to set
     */
    public void setResumeID(BigInteger resumeID) {
        this.resumeID = resumeID;
    }

    /**
     * @return the userID
     */
    public BigInteger getUserID() {
        return this.userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(BigInteger userID) {
        this.userID = userID;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the objective
     */
    public String getObjective() {
        return objective;
    }

    /**
     * @param objective the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * @return the experience
     */
    public String getExperience() {
        return experience;
    }

    /**
     * @param experience the experience to set
     */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /**
     * @return the accomplishments
     */
    public String getAccomplishments() {
        return accomplishments;
    }

    /**
     * @param accomplishments the accomplishments to set
     */
    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    /**
     * @return the certificationsList
     */
    public ArrayList<Certification> getCertificationsList() {
        return certificationsList;
    }

    /**
     * @param certificationsList the certificationsList to set
     */
    public void setCertificationsList(ArrayList<Certification> certificationsList) {
        this.certificationsList = certificationsList;
    }

    /**
     * @return the coursesList
     */
    public ArrayList<Course> getCoursesList() {
        return coursesList;
    }

    /**
     * @param coursesList the coursesList to set
     */
    public void setCoursesList(ArrayList<Course> coursesList) {
        this.coursesList = coursesList;
    }

    /**
     * @return the educationList
     */
    public ArrayList<Education> getEducationList() {
        return educationList;
    }

    /**
     * @param educationList the educationList to set
     */
    public void setEducationList(ArrayList<Education> educationList) {
        this.educationList = educationList;
    }

    /**
     * @return the interestsList
     */
    public ArrayList<Interest> getInterestsList() {
        return interestsList;
    }

    /**
     * @param interestsList the interestsList to set
     */
    public void setInterestsList(ArrayList<Interest> interestsList) {
        this.interestsList = interestsList;
    }

    /**
     * @return the membershipsList
     */
    public ArrayList<Membership> getMembershipsList() {
        return membershipsList;
    }

    /**
     * @param membershipsList the membershipsList to set
     */
    public void setMembershipsList(ArrayList<Membership> membershipsList) {
        this.membershipsList = membershipsList;
    }

    /**
     * @return the referencesList
     */
    public ArrayList<Reference> getReferencesList() {
        return referencesList;
    }

    /**
     * @param referencesList the referencesList to set
     */
    public void setReferencesList(ArrayList<Reference> referencesList) {
        this.referencesList = referencesList;
    }

    /**
     * @return the skillsList
     */
    public ArrayList<Skill> getSkillsList() {
        return skillsList;
    }

    /**
     * @param skillsList the skillsList to set
     */
    public void setSkillsList(ArrayList<Skill> skillsList) {
        this.skillsList = skillsList;
    }
}
