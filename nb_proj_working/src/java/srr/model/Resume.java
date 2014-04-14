package srr.model;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import srr.utilities.DbUtilities;
import static srr.utilities.StringUtilities.cleanMySqlInsert;
import static srr.utilities.StringUtilities.encodePassword;

/**
 * A class to represent a resume
 *
 * @author Jose Marte
 * @author stevenschilinski
 */
public class Resume {

    private BigInteger resumeID = null; //AUTO_INCREMENT
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
    private ArrayList<Experience> experienceList;
    private DbUtilities db;
    private ResultSet rs;

    /**
     * Empty constructor
     */
    public Resume() {

    }

    /**
     * Constructor takes in an ID and loads the record from the database
     *
     * @param ID The ID of the resume in the database
     */
    public Resume(BigInteger ID) {
        this.resumeID = ID;
        loadFromDb();
    }

    /**
     * Constructor takes in a title to instantiate this object
     *
     * @param title
     */
    public Resume(String title) {
        this.userID = null;
        this.resumeID = null;
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
     * Helper method to load the resume given a String ID
     *
     * @param ID The ID as a String
     * @return True on success
     */
    public boolean loadFromString(String ID) {
        this.resumeID = new BigInteger(ID);
        System.out.println("LATEST: this resume's ID: " + this.getResumeID());
        return (loadFromDb());

    }

    /**
     * Constructor takes in an ID and populates all fields from the database
     *
     * @param ID The record's id as a String
     *
     */
    private boolean loadFromDb() {
        if (this.resumeID == null) {
            return false;
        }
        boolean success = false;

        db = new DbUtilities();
        try {
            rs = db.getResultSet("SELECT * FROM srr.student_resume WHERE resumeID ='" + this.resumeID + "'");
            //set the title
            if (rs.next()) {
                this.setTitle(rs.getString("title"));
            }

            rs = db.getResultSet("SELECT * FROM srr.summary WHERE resumeID ='"
                    + this.resumeID + "'");
            if (rs.next()) {
                this.setObjective(rs.getString("objective"));
                this.setExperience(rs.getString("experience"));
                this.setAccomplishments(rs.getString("accomplishments"));
            }
            rs = db.getResultSet("SELECT * FROM srr.resume_skill WHERE resumeID ='"
                    + this.resumeID + "'");
            while (rs.next()) {
                if (this.skillsList == null) {
                    this.skillsList = new ArrayList<>();
                }
                this.skillsList.add(new Skill(BigInteger.valueOf(rs.getLong("skillID"))));
            }
            success = true;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("Error while trying to load the resume: " + ex.getStackTrace());
            success = false;
        } finally {
            db.releaseConnection();

        }
        return success;

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

    /**
     * Commit for the first time
     *
     * @param account
     * @return
     */
    public boolean commitToDb(StudentAccount account) {
        boolean success = false;
        String sql = "";
        ResultSet rs;

        if (this.resumeID != null) {
            return true; //already saved, must use update() to make changes
        }
        sql = "INSERT INTO srr.student_resume (resumeID, userID, title) "
                + "VALUES (NULL, " + account.getStudentID() + ", '" + this.title + "');";

        this.db = new DbUtilities();
        try {
            rs = db.executeQuery(sql);
            if (rs.next()) {//grab the generated ID
                this.resumeID = BigInteger.valueOf(rs.getLong(1));
                //NOW SAVE THE SKILLS
                if (this.skillsList != null) {
                    for (Skill skill : this.skillsList) {
                        skill.commitToDb(this.resumeID);
                    }
                }
                success = true;
            } else {
                return success; //failed to commit to db
            }

        } catch (SQLException ex) {
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } // NOW FOR THE SUMMARY
        sql = "INSERT INTO srr.summary VALUES(" + this.resumeID + ", '" + cleanMySqlInsert(this.objective) + "', '";
        sql += cleanMySqlInsert(this.experience) + "', '" + cleanMySqlInsert(this.accomplishments) + "');";
        this.db = new DbUtilities();
        try {
            rs = db.executeQuery(sql);
            rs = null;
            success = true;
        } catch (SQLException ex) {
            System.out.println("The sql string: " + sql); //*****************
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return success;
    }

    /**
     * A getter method to obtain the resume's ID
     *
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
        if (objective == null) {
            objective = "";
        }
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
        if (experience == null) {
            experience = "";
        }
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
        if (accomplishments == null) {
            accomplishments = "";
        }
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
        return this.skillsList;
    }

    /**
     * @param skillsList the skillsList to set
     */
    public void setSkillsList(ArrayList<Skill> skillsList) {
        this.skillsList = skillsList;
    }

    /**
     * Updates this resume in the DB
     *
     * @return True if successful, false otherwise
     */
    public boolean update() {
        boolean success = false;
        ResultSet rs;
        String sqlTitleUpdate = "UPDATE srr.student_resume SET title='" + cleanMySqlInsert(this.title) + "' WHERE resumeID=" + this.resumeID;
        String sqlSummaryUpdate = "UPDATE srr.summary SET objective='" + cleanMySqlInsert(this.objective) + "',  experience='"
                + cleanMySqlInsert(this.experience) + "', accomplishments='" + cleanMySqlInsert(this.accomplishments) + "'"
                + " WHERE resumeID=" + this.resumeID;

        //resume must already exist
        if (this.resumeID == null) {
            return false;
        }
      
        try {
            this.db = new DbUtilities();
            rs = db.executeQuery(sqlTitleUpdate);
            rs = db.executeQuery(sqlSummaryUpdate);

            //skills insert..................
            if (this.skillsList != null) {
                for (Skill skill : this.skillsList) {
                    if (skill.getSkillID() == null) {
                        skill.commitToDb(this.resumeID);
                    }
                }
            } else {
                System.out.println("The skills list is null!");
            }
            //Experience insert..................
            if (this.experienceList != null) {
                for (Experience exp : this.experienceList) {
                    if (exp.getExperienceID() == null) {
                        exp.commitToDb(this.resumeID);
                    }
                }
            } else {
                System.out.println("The experience list is null!");
            }
            success = true;
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            db.releaseConnection();
        }
        return success;

    }

    public void addSkill(String skillText) {
        if (this.skillsList == null) {
            this.skillsList = new ArrayList<>();
        }
        this.skillsList.add(new Skill(skillText));
    }

    public void addExperience(Experience experience) {
        if (this.experienceList == null) {
            this.experienceList = new ArrayList<>();
        }
        this.experienceList.add(experience);
    }

    public boolean isCommitted() {
        if (this.resumeID == null) {
            return false;
        }
        return true;
    }

    /**
     * Helper method to remove records associated with a resume in order to
     * update it with new ones
     *
     * @return
     */
    public boolean clearDBRelations() {
        boolean success = false;

        //first, skills....
        try {
            for (Skill skill : this.skillsList) {
                skill.removeFromDb();
            }
//            //now experience
//            for (Experience exp : this.experienceList) {
//                exp.removeFromDb();
//            }
            success = true;
        } catch (NullPointerException ex) {
            System.out.println("Error while trying to remove skills and experiences from resume ID " + this.resumeID);
        }

        return success;
    }
}
